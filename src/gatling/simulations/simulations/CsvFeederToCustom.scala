package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeederToCustom extends BaseSimulation {


  var idNumbers = (1 to 10).iterator

 // val customFeeder = Iterator.continually(Map("gameId" -> idNumbers.next()))

  // this is another way to right the feeder above that is a bit clearer
  def getNextGameId() = Map("gameId" -> idNumbers.next())
  val customFeeder = Iterator.continually(getNextGameId())



  def getSpecificVideoGame() = {
    repeat(10) {
      // now call the feeder here
      feed(customFeeder).
        exec(http("Get Specific Video Game")
        .get("videogames/${gameId}")) // parameter for the gameId goes here
        .pause(1)
    }
  }

  val scn = scenario("Video Game DB")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)


}

