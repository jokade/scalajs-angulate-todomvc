// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/Directives.scala
package todomvc.example

import biz.enef.angular.Directive

import scala.scalajs.js
import scala.scalajs.js.Dictionary

class TodoItemDirective extends Directive {
  override val templateUrl = "/web/todo-item.html"

  override def isolateScope: Dictionary[String] = js.Dictionary(
    "todo" -> "=item",
    "fireOnRemove" -> "&onRemove",
    "fireOnChange" -> "&onChange"
  )


}
