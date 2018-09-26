package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt


class RampUsersLoadSimulation extends BaseSimulation {

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
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificVideoGame())
    .pause(5)
    .exec(getAllVideoGames())


  setUp(
    scn.inject(
            nothingFor(5 seconds), // do nothing for 5 seconds
           constantUsersPerSec(10) during (10 seconds),  // injects users at a constant rate, during a given duration. Users injects at regular intervals
            rampUsersPerSec(1) to (5) during (20 seconds)  // inject users from starting rate to target rate, during a given duration
    ).protocols(httpConf.inferHtmlResources()) // inferHtmlResources will fetch everything on the page (JS, CSS, images etc.)
  )

}
