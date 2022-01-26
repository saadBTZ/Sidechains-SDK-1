package com.horizen.csw

import com.horizen.cryptolibprovider.CswCircuitImplZendoo
import com.horizen.secret.{PrivateKey25519, PrivateKey25519Creator}
import com.horizen.utils.BytesUtils
import org.bouncycastle.math.ec.rfc8032.Ed25519
import org.junit.Assert.assertEquals
import org.junit.Test

import java.lang.reflect.Method

class CswCircuitUtilsTest {
  val cswCircuit = new CswCircuitImplZendoo()

  @Test
  def transformPrivateKey25519(): Unit = {
    val pk: PrivateKey25519 = PrivateKey25519Creator.getInstance().generateSecret("seed".getBytes)

    val scalar = cswCircuit.privateKey25519ToScalar(pk)

    // Using reflection execute Bouncy castle method to generate Public key from the scalar
    // and verify that resulting PubKey is the same.
    val pubKey = new Array[Byte](32)
    val m: Method = classOf[Ed25519].getDeclaredMethod("scalarMultBaseEncoded", classOf[Array[Byte]], classOf[Array[Byte]], classOf[Int])
    m.setAccessible(true)
    m.invoke(classOf[Ed25519], scalar, pubKey, Integer.valueOf(0))

    assertEquals("Different public key than the one generated by BouncyCastle.",
      BytesUtils.toHexString(pk.publicImage().pubKeyBytes()), BytesUtils.toHexString(pubKey))

    // Regression
    assertEquals("Different pub key", "f165e1e5f7c290e52f2edef3fbab60cbae74bfd3274f8e5ee1de3345c954a166", BytesUtils.toHexString(pk.publicImage().pubKeyBytes()))
    assertEquals("Different private key", "19b25856e1c150ca834cffc8b59b23adbd0ec0389e58eb22b3b64768098d002b", BytesUtils.toHexString(pk.privateKey()))
    assertEquals("Different transformation result", "50d5e4c0b15402013941a3c525c6af85e7ab8a2da39a59707211ddd53def965e", BytesUtils.toHexString(scalar))
  }
}
