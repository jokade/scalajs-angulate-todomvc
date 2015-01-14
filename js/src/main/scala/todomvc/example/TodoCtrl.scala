// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/TodoCtrl.scala
package todomvc.example

import biz.enef.angular.core.Location
import biz.enef.angular.{Scope, Controller}

import scala.scalajs.js
import scala.util.{Failure, Success}

class TodoCtrl(taskService: TaskService, $location: Location, $scope: Scope) extends Controller {
  var todos = js.Array[Task]()
  var newTitle = ""
  var allChecked = true
  var remainingCount = 0
  val location = $location
  var statusFilter = js.Object()

  val watchPath = (path: Any, _:Any, scope:Scope) => {
    statusFilter = path match {
      case "/active" => js.Dynamic.literal(completed = false)
      case "/completed" => js.Dynamic.literal(completed = true)
      case _ => js.Dynamic.literal()
    }
  }

  import scala.scalajs.js.Any.{fromFunction0, fromFunction3}
  $scope.$watch(() => $location.path(), watchPath)

  taskService.findAll() onComplete {
    case Success(res) => todos = res
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
        update()
      case Failure(ex) => handleError(ex)
    }
  }

  def remove(todo: Task): Unit = {
    taskService.delete(todo) onComplete {
      case Success(_) =>
        todos = todos.filter( _.id != todo.id )
        update()
      case Failure(ex) => handleError(ex)
    }
  }

  private def update(): Unit = {
    remainingCount = todos.count(! _.completed)
    allChecked = remainingCount == 0
  }

  private def handleError(ex: Throwable): Unit = js.Dynamic.global.console.error(s"An error has occurred: $ex")

}
