// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/TodoCtrl.scala
package todomvc.example

import biz.enef.angular.core.Location
import biz.enef.angular.{Scope, Controller}

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import js.Dynamic.literal
import scala.util.{Failure, Success}

class TodoCtrl(taskService: TaskService, $location: Location, $scope: Scope) extends Controller {
  val $dynamicScope = $scope.asInstanceOf[js.Dynamic]
  var todos = js.Array[Task]()
  var newTitle = ""
  var allChecked = true
  var remainingCount = 0
  var statusFilter = literal()

  $scope.$watch(() => $location.path(), (path: String) =>
    statusFilter = path match {
      case "/active" => literal(completed = false)
      case "/completed" => literal(completed = true)
      case _ => literal()
    }
  )

  taskService.findAll() onComplete {
    case Success(res) =>
      todos = res
      update()
    case Failure(ex) => handleError(ex)
  }

  def save(todo: Task): Unit = taskService.update(todo) onComplete {
    case Success(_) => update()
    case Failure(ex) => handleError(ex)
  }

  def add(): Unit = {
    val title = newTitle.trim
    if(title != "") add(Task(title))
  }

  private def add(todo: Task): Unit = {
    taskService.create(todo) onComplete {
      case Success(newTask) =>
        todos :+= newTask
        newTitle = ""
        update()
      case Failure(ex) => handleError(ex)
    }
  }

  def remove(todo: Task): Unit = {
    taskService.delete(todo).onComplete {
      case Success(_) =>
        todos = todos.filter( _.id != todo.id )
        update()
      case Failure(ex) => handleError(ex)
    }
  }

  def clearCompleted(): Unit = {
    taskService.clearCompleted() onComplete {
      case Success(_) =>
        todos = todos.filter( !_.completed )
        update()
      case Failure(ex) => handleError(ex)
    }
  }

  def markAll(completed: Boolean): Unit = taskService.markAll(completed) onComplete {
    case Success(_) =>
      todos.foreach( _.completed = completed )
      update()
    case Failure(ex) => handleError(ex)
  }


  private def update(): Unit = {
    remainingCount = todos.count(! _.completed)
    allChecked = remainingCount == 0
  }

  private def handleError(ex: Throwable): Unit = js.Dynamic.global.console.error(s"An error has occurred: $ex")

}
