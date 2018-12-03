package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class MakeObjectsLoop extends BaseSimulation {


  def getAllVideoGames() = {
    repeat(3) { // we can make this call repeat 3 times
      exec(
        http("Get All Video Games - 1st call")
          .get("videogames")
          .header("From", "TheFromHeader")
          .check(regex("""Resident Evil \d"""))
          .check(status.is(200)))
    }
  }

  def getSpecificVideoGame() = {
    during(5.seconds) {
      // can loop for a period, e,g. 10 seconds - remember to import scala.concurrent.duration.DurationInt
      exec(http("Get Specific Video Game")
        .get("videogames/2")
        .check(jsonPath("$.name").is("Gran Turismo 3"))
        .check(status.is(200)))
    }
  }

  val scn = scenario("Video Game DB")
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificVideoGame())
    .pause(10)
    .exec(getAllVideoGames())


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)



}
