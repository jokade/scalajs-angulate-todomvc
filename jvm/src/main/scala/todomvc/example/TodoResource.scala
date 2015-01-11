package todomvc.example

import surf.rest.RESTPath.IntNumber
import surf.{ServiceRef, ServiceRefFactory}
import surf.rest.RESTResource

class TodoServiceResource(service: ServiceRef) extends RESTResource {
  override def name: String = "todos"

  override def handler(implicit factory: ServiceRefFactory): ServiceRef = service

  override def child(path: List[String]): Option[RESTResource] = path match {
    case IntNumber(id) => Some(TodoResource(id)(service))
    case _ => None
  }
}

case class TodoResource(id: Int)(service: ServiceRef) extends RESTResource {
  override def name: String = id.toString

  override def handler(implicit factory: ServiceRefFactory): ServiceRef = service

  override def child(path: List[String]): Option[RESTResource] = None
}
