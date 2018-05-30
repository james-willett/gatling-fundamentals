package simulations

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

// also shows how to use the ElFileBody template
class CustomFeeder extends BaseSimulation {

  // declare variables
  var idNumbers = (11 to 20).iterator
  val rnd = new Random()
  val now = LocalDate.now()
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  // declare helper methods
  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def getRandomDate(startDate: LocalDate, random: Random): String = {
    startDate.minusDays(random.nextInt(30)).format(pattern)
  }

  // The custom feeder itself
  val customFeeder = Iterator.continually(Map(
    "gameId" -> idNumbers.next(),
    "name" -> ("Game-" + randomString(5)),
    "releaseDate" -> getRandomDate(now, rnd),
    "reviewScore" -> rnd.nextInt(100),
    "category" -> ("Category-" + randomString(6)),
    "rating" -> ("Rating-" + randomString(4))
  ))

  /* This is the way to do it inside the body */

//  def postNewGame() = {
//    repeat(5) {
//      feed(customFeeder).
//        exec(http("Post New Game")
//          .post("videogames/")
//          .body(StringBody(
//            "{" +
//            "\n\t\"id\": ${gameId}," +
//            "\n\t\"name\": \"${name}\"," +
//            "\n\t\"releaseDate\": \"${releaseDate}\"," +
//            "\n\t\"reviewScore\": ${reviewScore}," +
//            "\n\t\"category\": \"${category}\"," +
//            "\n\t\"rating\": \"${rating}\"\n}")
//          ).asJSON
//        .check(status.is(200)))
//        .pause(1)
//    }
//  }

  /* This is better, through a template file */

  def postNewGame() = {
    repeat(5) {
      // now call the feeder here
      feed(customFeeder).
        exec(http("Post New Game")
          .post("videogames")
          .body(ElFileBody("NewGameTemplate.json")).asJSON //template file goes in gating/resources/bodies
          .check(status.is(200)))
        .pause(1)
    }
  }

  val scn = scenario("Video Game DB")
    .exec(postNewGame())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)


}
