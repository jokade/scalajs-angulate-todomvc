package todomvc.example

import surf.rest.RESTResponse.{NotFound, OK}
import surf.rest.{RESTResponse, RESTResource, RESTService}
import upickle._

class TodoService extends RESTService {
  private var todos = Map.empty[Int,Task]
  private var nextId = 1

  override def handleGET(resource: RESTResource, params: Map[String, Any]): Unit = resource match {
    case TodoResource(id) => request ! todos.get(id).map( t => OK(write(t)) ).getOrElse(NotFound)
    case _ => request ! OK(write(todos.values))
  }

  override def handlePOST(resource: RESTResource, params: Map[String, Any], body: String): Unit = resource match {
    case _ => request ! OK(createTask(body))
  }


  override def handlePUT(resource: RESTResource, params: Map[String, Any], body: String): Unit = resource match {
    case TodoResource(id) => request ! updateTask(id,body)
    case _ => request ! RESTResponse.MethodNotAllowed
  }


  override def handleDELETE(resource: RESTResource, params: Map[String, Any]): Unit = resource match {
    case TodoServiceResource(_) => request ! clearCompleted()
    case TodoResource(id) => request ! deleteTask(id)
    case _ => request ! RESTResponse.MethodNotAllowed
  }

  def createTask(body: String) : String = {
    val data = read[Task](body).copy(id=nextId)
    todos += nextId -> data
    nextId += 1
    write(data)
  }

  def updateTask(id: Int, body: String) : RESTResponse = todos.get(id) match {
    case None => RESTResponse.NotFound
    case Some(_) =>
      val data = read[Task](body).copy(id=id)
      todos += id -> data
      RESTResponse.OK(write(data))
  }

  def deleteTask(id: Int) : RESTResponse = todos.get(id) match {
    case None => RESTResponse.NotFound
    case Some(x) =>
      todos -= id
      RESTResponse.NoContent
  }

  def clearCompleted() : RESTResponse = {
    todos = todos.filterNot( p => p._2.completed)
    RESTResponse.NoContent
  }
}


