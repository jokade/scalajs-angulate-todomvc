// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/Task.scala
package todomvc.example

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportAll

trait Task extends js.Object {
  var id: Int = js.native
  var title: String = js.native
  var completed: Boolean = js.native
}

object Task {
  def apply(title: String, completed: Boolean= false, id: Int = -1) : Task =
    js.Dynamic.literal(id=id, title=title, completed=completed).asInstanceOf[Task]
}
