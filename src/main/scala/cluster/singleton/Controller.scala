package cluster.singleton

import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings}
import cluster.singleton.Protocol.{CallBackInc, CallOnceSync}

object Controller {
  def props(implicit system: ActorSystem) =
    ClusterSingletonManager.props(
      Props[Controller],
      terminationMessage = PoisonPill,
      settings = ClusterSingletonManagerSettings(system)
    )
}

class Controller extends Actor with ActorLogging {

  private[this] var count = 0

  override def preStart(): Unit = log.info(s"spawn a controller singleton")

  override def receive: Receive = {
    case CallOnceSync => handleCallOnceSync()
  }

  private[this] def handleCallOnceSync(): Unit = {
    log.info(s"controller call once sync, from ${sender()}")
    count += 1
    sender() ! CallBackInc(count)
  }
}
