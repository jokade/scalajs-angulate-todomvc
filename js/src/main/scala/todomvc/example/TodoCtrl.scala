// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/TodoCtrl.scala
package todomvc.example

import biz.enef.angular.Controller

import scala.scalajs.js

class TodoCtrl(taskService: TaskService, $location: js.Object) extends Controller {

  println("I'm initialized")
}
