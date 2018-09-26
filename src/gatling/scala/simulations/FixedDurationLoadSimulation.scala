package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt


class FixedDurationLoadSimulation extends BaseSimulation {

  def getAllVideoGames() = {
    exec(
      http("Get All Video Games - 1st call")
        .get("videogames")
        .check(status.is(200)))
  }

  def getSpecificVideoGame() = {
    exec(http("Get Specific Video Game")
      .get("videogames/2")
      .check(status.is(200)))
  }

  val scn = scenario("Video Game DB")
      .forever() {
        exec(getAllVideoGames())
          .pause(5)
          .exec(getSpecificVideoGame())
          .pause(5)
          .exec(getAllVideoGames())
      }


  // Run for a fixed duration
  setUp(
    scn.inject(
            nothingFor(5 seconds),
            atOnceUsers(10),
            rampUsers(50) over (30 second)
    ).protocols(httpConf.inferHtmlResources()))
    .maxDuration(1 minute)



}
