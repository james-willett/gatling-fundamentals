package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CodeReuseWithObjects extends BaseSimulation {

  /* SAY THAT WE HAVE THIS SCENARIO , WITH 2 DIFFERENT CALLS */

//  .exec(http("Get All Video Games")
//    .get("videogames")
//    .check(status.is(200)))
//
//
//    .exec(http("Get specific game")
//      .get("videogames/1")
//      .check(status.in(200 to 210)))
//
//
//    .exec(http("Get All Video Games")
//      .get("videogames")
//      .check(status.is(200)))



  /* WE CAN PUT THOSE 2 CALLS INTO METHODS LIKE THIS */

  def getAllVideoGames() = {
    exec(http("Get All Video Games")
      .get("videogames")
      .check(status.is(200)))
  }

  def getSpecificVideoGame() = {
    exec(http("Get specific game")
      .get("videogames/1")
      .check(status.in(200 to 210)))
  }

  // Now we can rewrite the scenario like this
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
