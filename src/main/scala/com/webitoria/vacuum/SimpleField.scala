package com.webitoria.vacuum

import com.webitoria.util.Loggable
import org.slf4j.Logger
import scala.collection.mutable.{Set=>MSet}

class SimpleField(logger: Logger) extends RectField {

  private val garbage = MSet[Pos](cells:_*)

  override def width = 20
  override def height = 20
  override def allowedMoves = List(MoveUp, MoveDown, MoveLeft, MoveRight)
  override def hasGarbage(p: Pos) = !hasWall(p) && garbage.contains(p)
  override def pickGarbage(p: Pos) = garbage.remove(p)

  override def availableMoves(p: Pos) = {
    val moves = allowedMoves.filter{ m =>
      val newPos = m.newPos(p)
      contains(newPos) && !hasWall(newPos)
    }
    logger.debug(s"Available moves from $p are $moves")
    moves
  }

  override def hasWall(p: Pos) =
    (p.y == 4 && p.x >= 5 && p.x <= 15) ||
    (p.y == 5 && p.x == 15) ||
    (p.y == 6 && p.x == 15) ||
    (p.y == 7 && p.x >= 5 && p.x <= 15)

}
