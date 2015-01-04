package com.webitoria.vacuum.snippet

import com.webitoria.util.Loggable
import com.webitoria.vacuum.{Pos, Simulation, Field}
import reactive.{Signal, Observing, Var}
import reactive.web.{PropertyVar, Page, Cell, CanRenderDomMutationConfig}
import reactive.web.html.Span
import scala.xml.{Elem, Text, NodeSeq}



case class FieldCell(x: Int, y: Int, hasWall: Boolean, hasGarbage: Boolean, hasRobot: Signal[Boolean])(implicit config: CanRenderDomMutationConfig, obs: Observing) extends Span with Cell with Loggable {

  override def baseElem = <td></td>

  def renderer = config.domMutationRenderer

  private def classNameByState(hasWall: Boolean) = {
    if (hasWall) "hasWall"
    else ""
  }
  private val className = PropertyVar("class")(classNameByState(hasWall))

  override def properties = Seq(className)

  if (!hasWall)
    hasRobot =>> { bool => className.update( classNameByState(hasWall) ) }

  val content: Signal[NodeSeq] = {
    hasRobot.map{ robot =>
      if (hasWall) NodeSeq.Empty
      else {
        if (robot)
          <span class="glyphicon glyphicon-tower" style="color: red;"></span>
        else {
          if (hasGarbage)
            <span class="glyphicon glyphicon-tree-conifer" style="color: #555555;"></span>
          else
            NodeSeq.Empty
        }
      }
    }
  }

}

class ReactiveView(field: Field, robotPos: Signal[Pos])(implicit obs: Observing, page: Page) {

  private val playground = {
    (for ( y <- Range(0, field.height) ) yield {
      (for ( x <- Range(0, field.width) ) yield {
        val pos = Pos(x, y)
        FieldCell(x,y, field.hasWall(pos), field.hasGarbage(pos), robotPos.map(_==pos))
      }).toArray
    }).toArray
  }

  def render: NodeSeq = {
    <table class="grid">
      {
      for (y <- Range(0, field.height)) yield {
        <tr>
          { for {x <- Range(0, field.width)} yield playground(y)(x).render }
        </tr>
      }
      }
    </table>
  }

}

