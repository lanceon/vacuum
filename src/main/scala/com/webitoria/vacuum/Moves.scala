package com.webitoria.vacuum

case class Pos(x: Int, y: Int) {

  override def toString = s"($x,$y)"

  override def equals(obj: Any) = super.equals(obj) || (obj match {
    case Pos(a,b) => a==x && b==y
    case _ => false
  })

  override def hashCode() = x * 1024 + y

}

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

