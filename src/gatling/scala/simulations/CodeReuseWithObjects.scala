package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CodeReuseWithObjects extends BaseSimulation {

  /* SAY THAT WE HAVE THIS SCENARIO , WITH 2 DIFFERENT CALLS */

//  val scn = scenario("Video Game DB")
//
//    .exec(http("Get All Video Games - 1st call")
//      .get("videogames")
//        .header("From", "TheFromHeader")
//      .check(regex("""Resident Evil \d"""))
//      .check(status.is(200)))
//
//    .pause(5)
//
//    .exec(http("Get Specific Video Game")
//      .get("videogames/2")
//      .check(jsonPath("$.name").is("Gran Turismo 3"))
//      .check(status.is(200)))
//
//    .pause(5)
//
//    .exec(http("Get All Video Games - 2nd call")
//      .get("videogames")
//        .header("From", "TheFromHeader")
//      .check(regex("""Resident Evil \d"""))
//      .check(status.is(200)))



  /* WE CAN PUT THOSE 2 CALLS INTO METHODS LIKE THIS */

  def getAllVideoGames() = {
      exec(
        http("Get All Video Games - 1st call")
          .get("videogames")
          .header("From", "TheFromHeader") // example of adding a header
          .check(regex("""Resident Evil \d"""))
          .check(status.is(200)))
  }

  def getSpecificVideoGame() = {
    exec(http("Get Specific Video Game")
      .get("videogames/2")
      .check(jsonPath("$.name").is("Gran Turismo 3"))
      .check(status.is(200)))
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
