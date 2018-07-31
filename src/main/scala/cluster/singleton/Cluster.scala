package cluster.singleton

import akka.actor.ActorSystem

object Cluster {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("cluster-singleton-system")

    val controller = system.actorOf(Controller.props, name = "controller")

    val proxy = system.actorOf(Proxy.props)
  }
}
