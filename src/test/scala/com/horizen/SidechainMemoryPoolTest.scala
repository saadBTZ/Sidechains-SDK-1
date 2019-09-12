package com.horizen

import org.scalatest.junit.JUnitSuite
import org.junit.{Before, Test}
import org.junit.Assert._

import com.horizen.fixtures._
import scorex.util.ModifierId

class SidechainMemoryPoolTest
  extends JUnitSuite
  with SidechainMemoryPoolFixture
  with TransactionFixture
  with SidechainTypes
{

  @Test def remove(): Unit = {
    val memoryPool = getSidechainMemoryPool()
    val tx = getTransaction()
    val txId : ModifierId = ModifierId(tx.id)


    assertEquals("Put operation must be success.", memoryPool.put(tx).isSuccess, true);
    assertEquals("Size must be 1.", memoryPool.size, 1)
    assertEquals("MemoryPool must contain transaction" + txId, memoryPool.modifierById(txId).get, tx)

    assertEquals("Put operation must be success.", memoryPool.put(getCompatibleTransaction()).isSuccess,
      true)
    assertEquals("Size must be 2.", memoryPool.size, 2)

    memoryPool.remove(tx)
    assertEquals("Size must be 1.", memoryPool.size, 1)

  }

  @Test def put(): Unit = {
    val memoryPool = getSidechainMemoryPool()
    val tx = getTransaction()
    val txCompat = getCompatibleTransaction()
    val txIncompat = getIncompatibleTransaction()
    val txId : ModifierId = ModifierId(tx.id)

    assertEquals("Put operation must be success.", memoryPool.put(tx).isSuccess, true);
    assertEquals("Size must be 1.", memoryPool.size, 1)
    assertEquals("MemoryPool must contain transaction " + txId, memoryPool.modifierById(txId).get, tx)
    assertEquals("MemoryPool must contain transaction " + txId, memoryPool.contains(txId), true)

    assertEquals("Put operation must be success.", memoryPool.put(txCompat).isSuccess,
          true)
    assertEquals("Size must be 2.", memoryPool.size, 2)
    assertEquals("MemoryPool must contain transaction " + txCompat.id, memoryPool.modifierById(ModifierId(txCompat.id)).get, txCompat)
    assertEquals("MemoryPool must contain transaction " + txCompat.id, memoryPool.contains(ModifierId(txCompat.id)), true)

    assertEquals("Put operation must be failure.", memoryPool.put(txIncompat).isSuccess,
      false)
    assertEquals("Size must be 2.", memoryPool.size, 2)
    assertEquals("MemoryPool must not contain transaction " + txIncompat.id,
      memoryPool.contains(ModifierId(txIncompat.id)), false)
    assertEquals("MemoryPool must not contain transaction " + txIncompat.id,
      1, memoryPool.notIn(Seq(ModifierId(txIncompat.id))).size)

    assertEquals("Take must return transaction " + tx.id, tx, memoryPool.take(1).head)
    assertEquals("Take with custom sort function must return transaction " + txCompat.id, txCompat,
      memoryPool.take( (a,b) => {
        if (a.fee() < b.fee())
          true
        else
          false
      }, 1).head)

    val mp = memoryPool.filter(List(tx))

    assertEquals("After applying of filter size must be 1.", memoryPool.size, 1)
    assertEquals("MemoryPool must contain transaction " + txCompat.id, memoryPool.modifierById(ModifierId(txCompat.id)).get, txCompat)
    assertEquals("MemoryPool must contain transaction " + txCompat.id, memoryPool.contains(ModifierId(txCompat.id)), true)
  }

  @Test def putSeq(): Unit = {
    val memoryPool = getSidechainMemoryPool()
    val txLst = getTransactionList()
    val txId : ModifierId = ModifierId(txLst.head.id)

    val txIncompat = txLst ::: getIncompatibleTransactionList()
    assertEquals("Put operation must be failure.", memoryPool.put(txIncompat).isSuccess, false);
    assertEquals("Size must be 0.", memoryPool.size, 0)

    assertEquals("Put operation must be success.", memoryPool.put(txLst).isSuccess, true);
    assertEquals("Size must be 1.", memoryPool.size, 1)
    assertEquals("MemoryPool must contain transaction " + txId, memoryPool.modifierById(txId).get, txLst.head)

    assertEquals("Put operation must be success.", memoryPool.put(getCompatibleTransactionList()).isSuccess,
      true)
    assertEquals("Size must be 2.", memoryPool.size, 2)

    assertEquals("Put operation must be failure.", memoryPool.put(getIncompatibleTransactionList()).isSuccess,
      false)
    assertEquals("Size must be 2.", memoryPool.size, 2)
  }

  @Test def putWithoutCheck(): Unit = {
    val memoryPool = getSidechainMemoryPool()
    val txLst = getTransactionList()
    val txId : ModifierId = ModifierId(txLst.head.id)

    val txIncompat = txLst ::: getIncompatibleTransactionList()
    assertEquals("Put operation must be failure.", memoryPool.put(txIncompat).isSuccess, false);
    assertEquals("Size must be 0.", memoryPool.size, 0)

    assertEquals("Put operation must be success.", memoryPool.put(txLst).isSuccess, true);
    assertEquals("Size must be 1.", memoryPool.size, 1)
    assertEquals("MemoryPool must contain transaction " + txId, memoryPool.modifierById(txId).get, txLst.head)

    assertEquals("Put operation must be success.", memoryPool.put(getCompatibleTransactionList()).isSuccess,
      true)
    assertEquals("Size must be 2.", memoryPool.size, 2)

    assertEquals("Put operation must be failure.", memoryPool.put(getIncompatibleTransactionList()).isSuccess,
      false)
    assertEquals("Size must be 2.", memoryPool.size, 2)
  }
}
