package com.webitoria.vacuum

import com.webitoria.util.Loggable

class SimpleField extends RectField with Loggable {

  override def width = 20
  override def height = 10
  override def allowedMoves = List(MoveUp, MoveDown, MoveLeft, MoveRight)

  override def availableMoves(p: Pos) = {
    val moves = allowedMoves.filter{ m =>
      val newPos = m.newPos(p)
      contains(newPos) && !hasWall(newPos)
    }
    logger.info(s"Available moves from $p are $moves")
    moves
  }

  override def hasWall(p: Pos) =
    (p.y == 4 && p.x >= 5 && p.x <= 15) ||
    (p.y == 5 && p.x >= 5 && p.x <= 10)

}
