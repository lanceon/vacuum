package com.webitoria.vacuum.snippet

import com.webitoria.util.{Loggable, MemoryInfo}
import com.webitoria.vacuum.Field._
import com.webitoria.vacuum._
import com.webitoria.vacuum.ui.PageSnippet
import net.liftweb.http.StatefulSnippet
import net.liftweb.util.BindHelpers
import reactive.web.javascript.Javascript
import reactive.{Signal, Var, Timer}
import reactive.web._
import reactive.web.javascript._

import scala.xml.{Text, Elem, NodeSeq}

class SimSnippet extends PageSnippet {

  val field = new SimpleField
  val startPos = Pos(1,1)
  val robot = new Robot("Vac-1", field, startPos)

  class FieldCell(x: Int, y: Int) extends RElem with Loggable {

    override def events = Nil
    override def baseElem = <span></span>
    override def properties = Seq(hasGarbage, hasRobot, hasWall)

    val hasGarbage = PropertyVar("garbage")(false)
    val hasWall = PropertyVar("wall")(false)
    val hasRobot = PropertyVar("robot")(false)

    override def render(implicit page: Page): Elem = {
      logger.info(s"cell render: $x, $y")
      if (hasRobot.value) <span>X</span> else <span></span>
    }

  }

  class View(field: Field) {

    private val playground = {
      val rows = (for ( y <- Range(0, field.height) ) yield {
        val cells = (for ( x <- Range(0, field.width) ) yield new FieldCell(x,y)).toArray
        cells
      }).toArray
      rows
    }

    def updateState(state: Simulation#SimState) = {

      logger.info("View.updateStatus() called")

      for ( y <- Range(0, field.height);  x <- Range(0, field.width) ) {
        val cellPos = Pos(x,y)
        val c = playground(x)(y)
        val garbage: Boolean = state.field.hasGarbage(cellPos)
        logger.info(s"pos: $cellPos, garb = $garbage")
        c.hasGarbage.update( garbage )
        c.hasRobot.update( state.pos==cellPos )
        c.hasWall.update( state.field.hasWall(cellPos) )
      }

    }

    def render: NodeSeq = {
      <table class="grid">
      {
        for (y <- Range(0, field.height)) yield {
          <tr>
            { for {x <- Range(0, field.width)} yield <td>{ playground(y)(x).render }</td> }
          </tr>
        }
      }
      </table>
    }

  }

  val view = new View(field)
  val timer = new Timer(0, 1000)

  val sim = new Simulation(field, robot, moveLimit = 1000, timer, startPos)
  sim.subscribe(state => view.updateState(state))
  sim.subscribe(state => logger.info(MemoryInfo.get))

  val curValue = Var[Long](0)

  timer =>> { t =>
    logger.info(s"timer = $t")
    curValue.update(t)
  }

  class Button[T](name: String)(onClick: => T = Unit) extends RElem with Loggable {
    val clickEventSource = DomEventSource.click
    override def events = Seq(clickEventSource)
    override def baseElem: Elem = <button type="button" class="btn btn-default">{ name }</button>
    override def properties = Nil
    clickEventSource =>> { _ =>
      logger.info(s"Button [$name] clicked")
      onClick
    }
  }
  object Button {
    def apply[T](name: String)(onClick: => T) = {
      new Button(name)(onClick)
    }
  }

  val btnStart = Button("Start"){ sim.start() }

  val btnStop = Button("Stop"){ sim.stop() }

  def render: NodeSeq => NodeSeq = {
    import BindHelpers._
    "data-bind=start" #> btnStart &
    "data-bind=stop" #> btnStop &
    "data-bind=table" #> { (ns:NodeSeq) => view.render }
  }

}
