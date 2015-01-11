package todomvc.example

import surf.rest.RESTResponse.{NotFound, OK}
import surf.rest.{RESTResource, RESTService}
import upickle._

class TodoService extends RESTService {
  private var todos = Map.empty[Int,Task]
  private var nextId = 1

  override def handleGET(resource: RESTResource, params: Map[String, Any]): Unit = resource match {
    case TodoResource(id) => request ! todos.get(id).map( t => OK(write(t)) ).getOrElse(NotFound)
    case _ => request ! OK(write(todos))
  }

  override def handlePOST(resource: RESTResource, params: Map[String, Any], body: String): Unit = resource match {
    case _ => request ! OK(createTask(body))
  }

  def createTask(body: String) : String = {
    val data = read[Task](body).copy(id=nextId)
    todos += nextId -> data
    nextId += 1
    write(data)
  }
}


