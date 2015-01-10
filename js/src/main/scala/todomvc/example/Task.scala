// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/Task.scala
package todomvc.example

import scala.scalajs.js.annotation.JSExportAll

@JSExportAll
case class Task(var title: String, var completed: Boolean = false, val id: Long = -1)
