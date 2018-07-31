package cluster.singleton

object Protocol {
  case object CallOnceSync

  case class CallBackInc(num: Int)
}
