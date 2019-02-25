package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class SimRunner extends BaseSimulation {

  setUp(
    Simulation1.scn1.inject(atOnceUsers(1)).protocols(httpConf),
    Simulation2.scn2.inject(atOnceUsers(1)).protocols(httpConf)
  )

}
