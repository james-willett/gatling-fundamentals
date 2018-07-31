package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseBodyAndExtract extends BaseSimulation {


  val scn = scenario("Video Game DB")

    // use jsonPath to check for a value
    .exec(http("Get specific game")
    .get("videogames/1")
    .check(jsonPath("$.name").is("Resident Evil 4"))) // link to jsonPath document / tutorial

    // use jsonPath to extract a value and save into a variable called "gameId"
    .exec(http("Get All Video Games - 2nd call")
    .get("videogames")
    .check(jsonPath("$[1].id").saveAs("gameId")))
    .exec { session => println(session); session }


    // reuse the saved variable "gameId" as a parameter in the next call
    .exec(http("Get specific game - 2nd call with parameter")
    .get("videogames/${gameId}")
    .check(jsonPath("$.name").is("Gran Turismo 3"))
    .check(bodyString.saveAs("responseBody")))
    .exec { session => println(session("responseBody").as[String]); session} // this will print it out


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
