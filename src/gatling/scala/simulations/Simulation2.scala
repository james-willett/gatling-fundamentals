package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Simulation2 extends BaseSimulation {

  val scn2 = scenario("Scenario2")
    .exec(http("Get All Video Games - 1st call")
      .get("videogames"))
    .pause(5) // pause for 5 seconds

}
