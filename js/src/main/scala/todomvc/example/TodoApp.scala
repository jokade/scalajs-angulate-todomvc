package todomvc.example

import biz.enef.angular.Angular

import scala.scalajs.js.JSApp

object TodoApp extends JSApp {
  override def main(): Unit = {
    val module = Angular.module("todomvc", Nil)


  }
}
