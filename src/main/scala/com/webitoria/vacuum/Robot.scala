package com.webitoria.vacuum

import com.webitoria.util.Loggable

import scala.util.Random

class Robot(name: String,
            field: Field,
            startPos: Pos) extends Loggable {

  var path: List[Pos] = Nil
  private var pos: Pos = startPos

  def getPos: Pos = pos

  def onTick() = {
    logger.info(s"$name: handling tick")
    pos = move match {
      case Some(m) =>
        val to = m.newPos(pos)
        logger.info(s"Moving $pos --> $to")
        path = to :: path
        to
      case _ =>
        logger.info(s"Staying at $pos")
        pos
    }
  }

  /**
   * Suggest move to next position if movement is available
   */
  def move: Option[Move] = {
    field.availableMoves(pos) match {
      case list @ x :: xs =>
        val move = list(Random.nextInt(list.length))
        Some(move)
      case _ =>
        None
    }

  }

  def onEnd() = {
    logger.info(
      "Mission completed\n" +
      "Path:\n" + path.reverse.mkString(" --> ")
    )
  }

}
