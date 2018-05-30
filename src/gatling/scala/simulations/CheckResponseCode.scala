package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

// this class also shows how to debug and print out session info
class CheckResponseCode extends BaseSimulation {

  val scn = scenario("Video Game DB")

    .exec(http("Get All Video Games - 1st call")
      .get("videogames")
        .check(status.is(200))) // check for a specific status
        .exec { session => println(session); session } // this will give some high level data, but not that interesting as we don't have any variables!


    .exec(http("Get specific game")
      .get("videogames/1")
        .check(status.in(200 to 210)) // check status is in a range
        .check(bodyString.saveAs("responseBody"))) // this will save the whole response body
        .exec { session => println(session("responseBody").as[String]); session} // this will print it out


    .exec(http("Get All Video Games - 2nd call")
      .get("videogames")
        .check(status.not(404), status.not(500))) // check status is not something

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)


}