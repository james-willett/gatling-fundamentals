package baseConfig

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BaseSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/app/")
    .proxy(Proxy("localhost", 8888).httpsPort(8888))

}
