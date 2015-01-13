// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/Directives.scala
package todomvc.example

import biz.enef.angular._
import biz.enef.angular.core.{Attributes, JQLite}
import org.scalajs.dom.KeyboardEvent

import scala.scalajs.js
import scala.scalajs.js.{Function, UndefOr, Dictionary}

class TodoItemDirective extends Directive {
  override val templateUrl = "/web/todo-item.html"

  override def isolateScope: Dictionary[String] = js.Dictionary(
    "todo" -> "=item",
    "fireOnRemove" -> "&onRemove",
    "fireOnChange" -> "&onChange"
  )

  override type withController = TIDCtrl

}

class TIDCtrl extends DirectiveController {
  js.Dynamic.global.console.log(this.asInstanceOf[js.Dynamic])
  println(scope)
  println(element)
  println(attributes)
}

class TodoEscapeDirective extends Directive {

  override def postLink(scope: Scope, elem: JQLite, attrs: Attributes, controller: js.Dynamic): Unit = {
    js.Dynamic.global.console.log(elem)
    elem.on("keydown", (evt: KeyboardEvent)=>{
      println("CALLED")
      if(evt.keyCode == 27) scope.$apply(attrs("todoEscape"))
    })
  }
}


class TodoFocusDirective extends Directive {
  override def postLink(scope: Scope, element: JQLite, attrs: Attributes, controller: js.Dynamic): Unit = {
    scope.$watch(attrs("todoFocus"),
      (newVal: UndefOr[js.Any]) => if(newVal.isDefined) println("OK"))
  }
}
