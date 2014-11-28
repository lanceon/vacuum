package com.webitoria.vacuum

import com.webitoria.util.Loggable
import scala.io.StdIn

object Main extends Loggable {
 
  def main(args: Array[String]) {

    val field = new SimpleField
    val startPos = Pos(1,1)
    val robot = new Robot("Vac-1", field, startPos)
    
    val sim = new Simulation(field, robot, moveLimit = 1000, tickMillis = 500, startPos)
    sim.subscribe(state => Field.draw(state.field, state.pos))
    sim.start()

    logger.info("Press Enter to exit")
    StdIn.readLine()

    sim.stop()

  }

}
