import reactive.{Observing, Timer, EventSource, EventStream}

import scalaz._
import Scalaz._

trait Move
case object MoveUp extends Move
case object MoveDown extends Move
case object MoveLeft extends Move
case object MoveRight extends Move

case class Pos(x: Int, y: Int)

trait Field {
  def width: Int
  def height: Int
  def contains(x: Int, y: Int): Boolean
  def hasWall(x: Int, y: Int): Boolean
}

trait FieldWithRobot extends Field {
  def robotPosition: Pos
}

class Robot {
  var path: List[Pos] = Nil
}

object Field {

  def draw(field: FieldWithRobot): String = {
    (for {
      y <- Range(0, field.height)
      x <- Range(0, field.width)
    } yield {
      val pos = Pos(x,y)
      (field.contains(x, y) ? { field.hasWall(x,y) ? "#" | ((field.robotPosition==pos) ? "+" | "o") } | " ") +
        ((x === field.width-1) ? "\n" | "")
    }).mkString
  }

}

case object Tick

class Simulator(field: FieldWithRobot) extends Loggable {

  var score = 0

  implicit val observing = new Observing {}
  private val timer = new Timer
  val ticks = timer.map(_ => Tick)

  def start(): Unit = {

    ticks.foreach{ _ =>
      println("Tick")
    }

  }

  def stop() = {

  }


}

class SimpleField extends FieldWithRobot {

  override def width = 10
  override def height = 10
  override def robotPosition = Pos(1,1)

  override def contains(x: Int, y: Int) = {
    x >=0 && x <= 9 && y >=0 && y <= 9
  }

  override def hasWall(x: Int, y: Int) = {
    y == 4 && x >= 3 && x <= 7
  }

}



object Main extends Loggable {

  def main(args: Array[String]) {

    val field = new SimpleField
    println(Field.draw(field))

    val sim = new Simulator(field)
    sim.start()

    Thread.sleep(6000)

  }

}
