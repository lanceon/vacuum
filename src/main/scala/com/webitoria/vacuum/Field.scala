package com.webitoria.vacuum

import com.webitoria.util.Loggable
import scalaz.Scalaz._

trait Field {
  def width: Int
  def height: Int
  def allowedMoves: List[Move]
  def availableMoves(p: Pos): List[Move]
  def contains(p: Pos): Boolean
  def hasWall(p: Pos): Boolean = false
}

trait RectField extends Field {

  override def contains(p: Pos): Boolean = p.x >=0 && p.x <= width-1 && p.y >=0 && p.y <= height-1

}

object Field extends Loggable {

  def draw(field: Field, robotPos: Pos): String = {
    val fieldStr = (for {
      y <- Range(0, field.height)
      x <- Range(0, field.width)
    } yield {
      val cellPos = Pos(x,y)
      (field.contains(cellPos) ? { field.hasWall(cellPos) ? "#" | ((robotPos==cellPos) ? "+" | "o") } | " ") +
        ((x === field.width-1) ? "\n" | "")
    }).mkString
    logger.info(s"\n$fieldStr")
    fieldStr
  }

}