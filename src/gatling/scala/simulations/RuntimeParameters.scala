package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class RuntimeParameters extends BaseSimulation {


  // method that will get a property or default
  private def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

  // now specify the properties
  def userCount: Int = getProperty("USERS", "5").toInt
  def rampDuration: Int = getProperty("RAMP_DURATION", "10").toInt
  def testDuration: Int = getProperty("DURATION", "60").toInt

  // print out the properties at the start of the test
  before {
    println(s"Running test with ${userCount} users")
    println(s"Ramping users over ${rampDuration} seconds")
    println(s"Total Test duration: ${testDuration} seconds")
  }

  // add in test step(s)
  def getAllVideoGames() = {
    exec(
      http("Get All Video Games - 1st call")
        .get("videogames")
        .check(status.is(200)))
  }

  // add a scenario
  val scn = scenario("Video Game DB")
    .forever() { // add in the forever() method - users now loop forever
      exec(getAllVideoGames())
    }

  // setup the load profile
  // example command line: ./gradlew gatlingRun-simulations.RuntimeParameters -DUSERS=10 -DRAMP_DURATION=5 -DDURATION=30
  setUp(
    scn.inject(
      nothingFor(5 seconds),
      rampUsers(userCount) over (rampDuration seconds))
  )
    .protocols(httpConf)
    .maxDuration(testDuration seconds)
}
