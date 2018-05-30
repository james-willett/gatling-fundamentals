package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class LoadSimulation extends BaseSimulation {

  def getAllVideoGames() = {
    exec(
      http("Get All Video Games - 1st call")
        .get("videogames")
        .header("From", "TheFromHeader")
        .check(regex("""Resident Evil \d"""))
        .check(status.is(200)))
  }

  def getSpecificVideoGame() = {
    exec(http("Get Specific Video Game")
      .get("videogames/2")
      .check(jsonPath("$.name").is("Gran Turismo 3"))
      .check(status.is(200)))
  }

  val scn = scenario("Video Game DB")
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificVideoGame())
    .pause(5)
    .exec(getAllVideoGames())


//  // Load Simulation 1:  basic Load Simulation
//  setUp(
//    scn.inject(
//      nothingFor(5 seconds), // do nothing for 5 seconds
//      atOnceUsers(5), // inject 5 users at once
//      rampUsers(10) over (5 seconds) // inject 10 users over a period of 5 seconds
//    ).protocols(httpConf.inferHtmlResources()) // inferHtmlResources will fetch everything on the page (JS, CSS, images etc.)
//  )

  // Load Simulation 2: ramp up the users per second
  setUp(
    scn.inject(
      nothingFor(5 seconds), // do nothing for 5 seconds
      constantUsersPerSec(2) during (30 seconds),  // injects users at a constant rate, during a given duration. Users injects at regular intervals
      rampUsersPerSec(1) to 20 during (2 minutes)  // inject users from starting rate to target rate, during a given duration
    ).protocols(httpConf.inferHtmlResources()) // inferHtmlResources will fetch everything on the page (JS, CSS, images etc.)
  )

  // Load Simulation 3: run the scenario for a fixed duration
//  setUp(
//    scn.inject(
//      nothingFor(5 seconds),
//      atOnceUsers(10),
//      rampUsers(10) over (20 second))
//  )
//    .protocols(httpConf)
//    .maxDuration(2 minutes) // run for a maximum of 2 minutes - users keep looping





}
