package todomvc.example

import akka.actor.ActorSystem
import surf.akka.rest.SimpleRESTServer

object Server extends App {

  val actorSystem = ActorSystem("todomvc")

  val httpServer = SimpleRESTServer.fromRoute(actorSystem = actorSystem){ (ec, cf, fm) =>
    import akka.http.server.Directives._

    /* START PAGE */
    path("") {
      //getFromResource("web/index.html")
      getFromFile("jvm/src/main/resources/web/index.html")
    } ~
    /* RESOURCES */
    path("web" / Rest ) { path =>
      //getFromResource("web/"+path)
      getFromFile("jvm/src/main/resources/web/"+path) ~
      getFromFile("js/target/scala-2.11/"+path)
    }
    /* REST */
    //RESTRouter("rest", restRoot)(cf,serviceFactory,ec,fm)

  }

  Console.in.readLine()
  httpServer.stop()

}
