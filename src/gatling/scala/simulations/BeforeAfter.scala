package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BeforeAfter extends BaseSimulation {

  // add in before and after blocks like this - useful if you have test data setup or tear down
  before {
    println("Starting Performance Test")
  }

  after {
    println("Performance Test Complete")
  }

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

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
