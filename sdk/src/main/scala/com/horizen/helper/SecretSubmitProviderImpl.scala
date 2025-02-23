package com.horizen.helper
import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.horizen.SidechainNodeViewHolder.ReceivableMessages.LocallyGeneratedSecret
import com.horizen.secret.Secret

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
import scala.language.postfixOps

class SecretSubmitProviderImpl(sidechainNodeViewHolderRef: ActorRef) extends SecretSubmitProvider {

  implicit val timeout: Timeout = 20 seconds

  @throws(classOf[IllegalArgumentException])
  override def submitSecret(s: Secret): Unit = {
    Await.ready(sidechainNodeViewHolderRef ? LocallyGeneratedSecret(s), timeout.duration).value match {
      case Some(value) => value match {
        case Success(res) =>
          res match {
            case Success(_) => {}
            case Failure(exception) => throw new IllegalArgumentException(exception)
            case _ => throw new IllegalArgumentException("Fail to submit secret.")
          }
        case Failure(exception) => throw new IllegalArgumentException(exception)
      }
      case None => throw new IllegalStateException("Fail to submit secret.")
    }
  }
}
