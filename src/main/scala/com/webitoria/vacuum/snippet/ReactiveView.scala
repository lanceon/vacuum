package com.webitoria.vacuum.snippet

import com.webitoria.util.Loggable
import com.webitoria.vacuum.{Pos, Simulation, Field}
import reactive.Var
import reactive.web.{PropertyVar, Page, Cell, CanRenderDomMutationConfig}
import reactive.web.html.Span

import scala.xml.{Elem, Text, NodeSeq}


case class FieldCell(x: Int, y: Int, hasWall: Boolean)(implicit config: CanRenderDomMutationConfig) extends Span with Cell with Loggable {

  override def baseElem = <td></td>

  def renderer = config.domMutationRenderer

  val content = Var[NodeSeq]{
    if (hasWall) Text("W") else NodeSeq.Empty
  }

}

class ReactiveView(field: Field)(implicit page: Page) {

  private val playground = {
    (for ( y <- Range(0, field.height) ) yield {
      (for ( x <- Range(0, field.width) ) yield {
        val pos = Pos(x, y)
        FieldCell(x,y, field.hasWall(pos))
      }).toArray
    }).toArray
  }

  /*def updateState(state: Simulation#SimState) = {
    for ( y <- 0 to field.height ) {
      for ( x <- 0 to field.width ) {
        val cellPos = Pos(x,y)
        val cell = playground(x)(y).content
        val content = if (state.pos==cellPos) <span>*</span> else  <span>+</span>
        cell.update(content)
        //logger.info(s"updating cell: pos=${state.pos}, cellPos=$cellPos")
      }
    }
  }*/

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

