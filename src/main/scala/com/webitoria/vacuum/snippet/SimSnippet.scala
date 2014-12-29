package com.webitoria.vacuum.snippet

import com.webitoria.util.{Loggable, MemoryInfo}
import com.webitoria.vacuum.Field._
import com.webitoria.vacuum._
import com.webitoria.vacuum.ui.ReactivePage
import net.liftweb.http.StatefulSnippet
import net.liftweb.util.BindHelpers
import net.liftweb.util.BindHelpers._
import reactive.Timer
import reactive.web._

import scala.xml.{Elem, NodeSeq}

class SimSnippet extends StatefulSnippet with ReactivePage {

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
      super.render
    }

  }

  class View(field: Field) {

    private val table: Array[Array[FieldCell]] =
      (for ( y <- Range(0, field.height) ) yield {
        (for ( x <- Range(0, field.width) ) yield new FieldCell(x,y)).toArray
      }).toArray

    def updateState(state: Simulation#SimState) = {

      logger.info("View.updateStatus() called")

      for ( y <- Range(0, field.height);  x <- Range(0, field.width) ) {
        val cellPos = Pos(x,y)
        val c = table(x)(y)
        val garbage: Boolean = state.field.hasGarbage(cellPos)
        logger.info(s"pos: $cellPos, garb = $garbage")
        c.hasGarbage.update( garbage )
        c.hasRobot.update( state.pos==cellPos )
        //c.hasWall.update( state.field.hasWall(cellPos) )
      }

    }

    def render: NodeSeq = {
      <table class="grid">
      {
        for (y <- Range(0, field.height)) yield {
          <tr>
            { for {x <- Range(0, field.width)} yield <td>{ table(y)(x).render }</td> }
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
  sim.start()
  //sim.stop()

  override def dispatch = {
    case "table" => "*" #> view.render
  }

}
