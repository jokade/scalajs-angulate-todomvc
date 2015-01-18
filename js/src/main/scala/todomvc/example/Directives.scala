// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/Directives.scala
package todomvc.example

import biz.enef.angular._
import biz.enef.angular.core.{Timeout, Attributes, JQLite}
import org.scalajs.dom.KeyboardEvent

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExport}
import scala.scalajs.js.{Function, UndefOr, Dictionary}

class TodoItemDirective extends Directive {
  override val templateUrl = "/web/todo-item.html"

  override def isolateScope: Dictionary[String] = js.Dictionary(
    "todo" -> "=item",
    "fireOnRemove" -> "&onRemove",
    "fireOnChange" -> "&onChange"
  )

  override type withController = TodoItemDirective.Ctrl
}

object TodoItemDirective {

  @JSExportAll
  @ExportToScope("directive")
  class Ctrl($scope: js.Dynamic) extends Controller {

    def onEditStart(): Unit = {
      $scope.editing = true
      $scope.title = $scope.todo.title
    }

    def onEditEnd(): Unit = {
      $scope.editing = false
      $scope.todo.title = $scope.title

      $scope.fireOnChange()
    }

    def onEditCancel(): Unit = {
      $scope.editing = false
      $scope.title = $scope.todo.title
    }
  }
}



class TodoEscapeDirective extends Directive {

  override def postLink(scope: Scope, elem: JQLite, attrs: Attributes, controller: js.Dynamic): Unit = {
    elem.on("keydown", (evt: KeyboardEvent)=>{
      if(evt.keyCode == 27) scope.$apply(attrs("todoEscape"))
    })
  }
}


class TodoFocusDirective($timeout: Timeout) extends Directive {
  override def postLink(scope: Scope, element: JQLite, attrs: Attributes, controller: js.Dynamic): Unit = {
    val elem = element.head.asInstanceOf[js.Dynamic]

    scope.$watch(attrs("todoFocus"),
      (newVal: UndefOr[js.Any]) => if(newVal.isDefined) $timeout( () => elem.focus() ) )
  }
}
