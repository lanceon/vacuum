package org

import org.fields._
import org.robots.Robot
import org.util.Loggable
import reactive.{Observing, Timer}

trait Move
case object MoveUp extends Move
case object MoveDown extends Move
case object MoveLeft extends Move
case object MoveRight extends Move

case class Pos(x: Int, y: Int)

class Simulator(field: Field,
                robot: Robot,
                startPos: Pos = Pos(0,0)) extends Loggable {

  var score = 0
  var pos = startPos
  var stopFlag = false
  
  case object Tick

  implicit val observing = new Observing {}

  private val ticks = new Timer().map(_ => Tick).takeWhile(_ => !stopFlag)

  def start(): Unit = {
    logger.info("Starting...")
    stopFlag = false
    ticks.foreach{ _ =>
      logger.info("Tick")
      robot.onTick()
      Field.draw(field, pos)
    }
  }

  def stop() = {
    logger.info("Stopping...")
    stopFlag = true
  }

}

object Main extends Loggable {

  def main(args: Array[String]) {

    val field = new SimpleField
    val startPos = Pos(1,1)

    logger.info("Starting field:\n" + Field.draw(field, startPos))

    val robot = new Robot

    val sim = new Simulator(field, robot, startPos)
    sim.start()
    Thread.sleep(5000)
    sim.stop()

  }

}
