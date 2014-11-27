package org

import org.fields._
import org.robots.Robot
import org.util.Loggable
import reactive.{Observing, Timer}

import scala.io.{StdIn, Source}

/**
 * Field's (0, 0) is top left corner
 *
 * +--------> X
 * |
 * |
 * |
 * V Y
 *
 */

trait Move {
  def newPos(p: Pos): Pos
}
case object MoveUp extends Move {
  override def newPos(p: Pos) = Pos(p.x, p.y-1)
}
case object MoveDown extends Move {
  override def newPos(p: Pos) = Pos(p.x, p.y+1)
}
case object MoveLeft extends Move {
  override def newPos(p: Pos) = Pos(p.x-1, p.y)
}
case object MoveRight extends Move {
  override def newPos(p: Pos) = Pos(p.x+1, p.y)
}

case class Pos(x: Int, y: Int)

class Simulator(field: Field,
                robot: Robot,
                moveLimit: Int,
                tickMillis: Int,
                startPos: Pos = Pos(0,0)) extends Loggable {

  var score = 0
  var pos = startPos
  var stopFlag = false
  var moves = 0
  
  case object Tick

  implicit val observing = new Observing {}

  private val ticks = new Timer(0, tickMillis).map(_ => Tick).takeWhile(_ => !stopFlag)

  def start(): Unit = {
    logger.info("Starting...")

    Field.draw(field, startPos)

    stopFlag = false
    ticks.foreach{ _ =>
      logger.info("Tick")
      robot.onTick()
      moves = moves + 1
      pos = robot.getPos
      logger.info(s"Move #$moves")
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

    val robot = new Robot("Vac-1", field, startPos)

    val sim = new Simulator(field, robot, 1000, 500, startPos)
    sim.start()
    logger.info("Press Enter to exit")
    StdIn.readLine()
    sim.stop()

  }

}
