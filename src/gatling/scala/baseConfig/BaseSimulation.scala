package baseConfig

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BaseSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/app/")
    .header("Accept", "application/json")
  //  .proxy(Proxy("localhost", 8888).httpsPort(8888)) // uncomment this line if you want to run through a HTTP proxy such as Fiddler or Charles Proxy

}
