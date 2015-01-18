package todomvc.example

import biz.enef.angular.{Controller, Angular}

import scala.scalajs.js.JSApp

object TodoApp extends JSApp {
  override def main(): Unit = {
    val module = Angular.module("todomvc", Nil)

    module.controllerOf[TodoCtrl]("TodoCtrl")

    module.directiveOf[TodoItemDirective]
    module.directiveOf[TodoEscapeDirective]
    module.directiveOf[TodoFocusDirective]

    module.serviceOf[TaskService]

  }
}
