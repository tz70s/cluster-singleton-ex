package clustersingletonex

import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings}
import clustersingletonex.Controller.CallOnce

object Controller {
  case object CallOnce

  def props = Props(new Controller)
}

class Controller extends Actor with ActorLogging {
  import Controller._

  override def receive: Receive = {
    case CallOnce => println(s"controller call once, from ${sender()}")
  }
}

object Cluster {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("exsystem")

    val controllerRef = system.actorOf(
      ClusterSingletonManager.props(
        singletonProps = Controller.props,
        terminationMessage = PoisonPill,
        settings = ClusterSingletonManagerSettings(system)
      )
    )

    controllerRef ! CallOnce
  }
}
