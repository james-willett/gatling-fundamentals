package finalSimulation

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class VideoGameFullTestTemplate extends BaseSimulation{

  /*** HTTP Calls ***/
  def getAllVideoGames() = {
    exec(
      http("Get All Video Games")
        .get("videogames")
        .check(status.is(200)))
  }

  // .... add other calls here - Create new game, Get single game, Delete game


  /*** Scenario Design ***/

    // using the HTTP calls created, create a scenario that does the following:
  // 1. Get all games
  // 2. create new game
  // 3. get details of that single game
  // 4. delete the game


  /*** Setup Load Simulation ***/

  // create a scenario that uses rutime parameters for:
  // 1. Users
  // 2. Ramp up time
  // 3. Test duration

  /*** Custom Feeder ***/

  // to generate the data for the Create New Game JSON


  /*** Helper Methods ***/

  // for the custom feeder, or for the defaults of the runtime parameters... and anything else


  /*** Variables ***/

  // for the helper methods above
  // and the parameters for the runtime variables


  /*** Before & After  ***/
  // to print out messages at the start and end of the test

}
