package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeeder extends BaseSimulation {

  // this reads the csv file in src/gatling/resources/data
  val csvFeeder = csv("gameCsvFile.csv").circular // random is self explanatory, other options are .queue (error if run out) and .circular (goes back to start)

  def getSpecificVideoGame() = {
    repeat(10) {
      // now call the feeder here
      feed(csvFeeder).
        exec(http("Get Specific Video Game")
        .get("videogames/${gameId}") // parameter for the gameId goes here
        .check(jsonPath("$.name").is("${gameName}")) // and parameter to check the game name goes here
        .check(status.is(200)))
        .pause(1)
    }
  }

  val scn = scenario("Video Game DB")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)


}

