// This code is based on (10.01.2015):
// https://github.com/greencatsoft/scalajs-angular-todomvc/blob/master/scalajs/src/main/scala/todomvc/example/TaskService.scala
package todomvc.example

import biz.enef.angular.Service
import biz.enef.angular.core.{HttpConfig, HttpFuture, HttpService}

import scala.scalajs.js
import scala.scalajs.js.Date

class TaskService($http: HttpService) extends Service {

  def findAll(): HttpFuture[js.Array[Task]] = $http.get("/api/todos", HttpConfig("ts"-> Date.now))

  def create(task: Task) : HttpFuture[Task] = $http.post("/api/todos", task)

  def update(task: Task) : HttpFuture[Task] = $http.put(s"/api/todos/${task.id}", task)

  def delete(task: Task) : HttpFuture[Unit] = $http.delete(s"/api/todos/${task.id}")

  def clearAll() : HttpFuture[Unit] = $http.post("/api/todos/clearAll")

}
