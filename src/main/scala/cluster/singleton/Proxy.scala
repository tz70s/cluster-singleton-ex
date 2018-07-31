package cluster.singleton

import akka.actor.{Actor, ActorLogging, Props, Timers}
import akka.cluster.singleton.{ClusterSingletonProxy, ClusterSingletonProxySettings}
import cluster.singleton.Protocol.{CallBackInc, CallOnceSync}
import cluster.singleton.Proxy.{CallKey, CallTick}

import scala.concurrent.duration._

object Proxy {

  case object CallKey
  case object CallTick

  def props = Props(new Proxy)
}

class Proxy extends Actor with ActorLogging with Timers {

  private[this] val proxy = context.actorOf(
    ClusterSingletonProxy
      .props(singletonManagerPath = "/user/controller", settings = ClusterSingletonProxySettings(context.system))
  )

  override def preStart(): Unit = {
    timers.startPeriodicTimer(CallKey, CallTick, 5.seconds)
  }

  override def receive: Receive = {
    case CallTick =>
      proxy ! CallOnceSync

    case CallBackInc(inc) =>
      log.info(s"receive call back, with number $inc")
  }

}
