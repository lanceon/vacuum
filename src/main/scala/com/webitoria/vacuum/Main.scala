package com.webitoria.vacuum

import com.webitoria.util.{MemoryInfo, Loggable}
import reactive.{EventSource, Timer}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.StdIn
import scala.util.Try

object Main extends Loggable {
 
  def main(args: Array[String]) {

    val field = new SimpleField
    val startPos = Pos(1,1)
    val robot = new Robot("Vac-1", field, startPos)
    val timer = {
      def makeTimer(tickMillis: Int) = {
        /*if (tickMillis == 0) {
          val st = new EventSource[Tick.type]()
          Future {
            while (true) st.fire(Tick)
          }
          st
        }
        else*/
        new Timer(0, tickMillis)//.map(_ => Tick)
      }
      args.toList match {
        case x :: Nil => Try { Integer.parseInt(x) }.map(makeTimer).getOrElse(makeTimer(1000))
        case _ => makeTimer(1000)
      }
    }

    val sim = new Simulation(field, robot, moveLimit = 1000, timer, startPos)
    sim.subscribe(state => Field.draw(state.field, state.pos))
    sim.subscribe(state => logger.info(MemoryInfo.get))
    sim.start()

    logger.info("Press Enter to exit")
    StdIn.readLine()

    sim.stop()

  }

}
