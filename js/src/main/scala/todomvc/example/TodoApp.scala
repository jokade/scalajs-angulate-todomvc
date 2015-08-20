package todomvc.example

import biz.enef.angulate._
import todomvc.example.{TaskService, TodoCtrl}

import scala.scalajs.js.JSApp

object TodoApp extends JSApp {
  override def main(): Unit = {
    val module = angular.createModule("todomvc", Nil)

    TodoCtrl.init(module)

    module.directiveOf[TodoItemDirective]
    module.directiveOf[TodoEscapeDirective]
    module.directiveOf[TodoFocusDirective]

    module.serviceOf[TaskService]

  }
}
