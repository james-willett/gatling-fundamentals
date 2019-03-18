package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class SessionVariables extends BaseSimulation {

  def getAllVideoGames() = {
    exec(
      http("Get All Video Games")
        .get("videogames")
        .check(jsonPath("$[1].id").saveAs("gameId")))
  }

  def getSpecificVideoGame() = {
    exec(http("Get Specific Video Game")
      .get("videogames/${gameId}"))
  }

  val scn = scenario("Video Game DB")
    .exec(getAllVideoGames())
    .pause(5)
    .exec(getSpecificVideoGame())
    .pause(5)
      .exec(session => {session.set("gameId", ""); session})

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)


}