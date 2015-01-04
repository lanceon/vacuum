package com.webitoria.vacuum

import com.webitoria.util.Loggable
import scalaz._
import Scalaz._

trait Field {

  def width: Int
  def height: Int

  def allowedMoves: List[Move]
  def availableMoves(p: Pos): List[Move]

  val contains: Pos => Boolean
  def hasWall(p: Pos): Boolean = false
  def cells = ( for { y <- Range(0, height); x <- Range(0, width) if contains(Pos(x, y)) } yield Pos(x, y) ).toList

  def hasGarbage(p: Pos): Boolean
  def pickGarbage(p: Pos): Unit

}

trait RectField extends Field {

  // Valid positions on field are x = [0, width-1], y = [0, height-1]
  // memoization is not needed actually, just for scalaz fun
  override val contains = Memo.immutableHashMapMemo{
    (p: Pos) => (p.x >= 0 && p.x <= width - 1 && p.y >= 0 && p.y <= height - 1)
  }

}


object Field extends Loggable {

  def draw(field: Field, robotPos: Pos): String = {
    val fieldStr = (for {
      y <- Range(0, field.height)
      x <- Range(0, field.width)
    } yield {
      val cellPos = Pos(x,y)
      val cell = (field.hasGarbage(cellPos)) ? "." | " "
      (field.contains(cellPos) ? { field.hasWall(cellPos) ? "#" | ((robotPos==cellPos) ? "+" | cell) } | " ") +
        ((x === field.width-1) ? "\n" | "")
    }).mkString
    logger.info(s"\n$fieldStr")
    fieldStr
  }

}