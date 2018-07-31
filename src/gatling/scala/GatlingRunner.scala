import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import simulations._
import finalSimulation._


object GatlingRunner {

  def main(args: Array[String]): Unit = {

    // this is where you specify the class you want to run
    val simClass = classOf[VideoGameFullTest].getName

    val props = new GatlingPropertiesBuilder
    props.simulationClass(simClass)

    Gatling.fromMap(props.build)
  }

}
