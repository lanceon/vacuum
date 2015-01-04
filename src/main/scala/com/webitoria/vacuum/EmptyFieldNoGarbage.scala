package com.webitoria.vacuum

import org.slf4j.Logger

class EmptyFieldNoGarbage(logger: Logger) extends RectField {

  override def width = 20
  override def height = 10
  override def allowedMoves = List(MoveUp, MoveDown, MoveLeft, MoveRight)
  override def hasWall(p: Pos) = false
  override def hasGarbage(p: Pos) = false
  override def pickGarbage(p: Pos) = { }

  override def availableMoves(p: Pos) = {
    val moves = allowedMoves.filter{ m =>
      val newPos = m.newPos(p)
      contains(newPos) && !hasWall(newPos)
    }
    logger.info(s"Available moves from $p are $moves")
    moves
  }


}
