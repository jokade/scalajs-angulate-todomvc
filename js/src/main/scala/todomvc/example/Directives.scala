// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/Directives.scala
package todomvc.example

import biz.enef.angulate._
import biz.enef.angulate.core.{Timeout, Attributes, JQLite}
import org.scalajs.dom.KeyboardEvent

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExport}
import scala.scalajs.js.{Function, UndefOr, Dictionary}

class TodoItemDirective extends Directive {
  override type ControllerType = js.Dynamic
  override type ScopeType = js.Dynamic
  override val templateUrl = "/web/todo-item.html"
  override val controllerAs = "directive"


  override def controller(ctrl: ControllerType, scope: ScopeType, elem: JQLite, attrs: Attributes): Unit = {
    ctrl.onEditStart = () => {
      scope.editing = true
      scope.title = scope.todo.title
    }

    ctrl.onEditEnd = () => {
      scope.editing = false
      scope.todo.title = scope.title

      scope.fireOnChange()
    }

    ctrl.onEditCancel = () => {
      scope.editing = false
      scope.title = scope.todo.title
    }

  }

  override def isolateScope: Dictionary[String] = js.Dictionary(
    "todo" -> "=item",
    "fireOnRemove" -> "&onRemove",
    "fireOnChange" -> "&onChange"
  )
}


class TodoEscapeDirective extends Directive {
  override type ScopeType = Scope
  override type ControllerType = js.Dynamic
  override def postLink(scope: ScopeType, elem: JQLite, attrs: Attributes, controller: js.Dynamic): Unit = {
    elem.on("keydown", (evt: KeyboardEvent)=>{
      if(evt.keyCode == 27) scope.$apply(attrs("todoEscape"))
    })
  }
}


class TodoFocusDirective($timeout: Timeout) extends Directive {
  override type ControllerType = js.Dynamic
  override type ScopeType = Scope
  override def postLink(scope: ScopeType, element: JQLite, attrs: Attributes, controller: js.Dynamic): Unit = {
    val elem = element(0).asInstanceOf[js.Dynamic]

    scope.$watch(attrs("todoFocus"),
      (newVal: UndefOr[js.Any]) => if(newVal.isDefined) $timeout( () => elem.focus() ) )
  }
}
