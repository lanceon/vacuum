package com.webitoria.vacuum

/**
 * Created by Alexey.Zavalin on 28.11.2014.
 */

case class Pos(x: Int, y: Int)

trait Move {
  def newPos(p: Pos): Pos
}
case object MoveUp extends Move {
  override def newPos(p: Pos) = Pos(p.x, p.y-1)
}
case object MoveDown extends Move {
  override def newPos(p: Pos) = Pos(p.x, p.y+1)
}
case object MoveLeft extends Move {
  override def newPos(p: Pos) = Pos(p.x-1, p.y)
}
case object MoveRight extends Move {
  override def newPos(p: Pos) = Pos(p.x+1, p.y)
}

