package com.webitoria.vacuum

import com.webitoria.util.Loggable
import org.slf4j.Logger
import reactive.Signal

import scala.util.Random

class Robot(name: String,
            field: Field,
            logger: Logger) {

  //var path: List[Pos] = Nil

  // todo: use state monad
  def requestMove(pos: Pos): Pos = {
    logger.info(s"Robot <$name>: handling tick")
    move(pos) match {
      case Some(m) =>
        val to = m.newPos(pos)
        logger.info(s"Moving $pos --> $to")
        //path = to :: path
        to
      case _ =>
        logger.info(s"Staying at $pos")
        pos
    }
  }

  /**
   * Suggest move to next position if movement is available
   */
  protected def move(pos: Pos): Option[Move] = {
    field.availableMoves(pos) match {
      case list @ x :: xs =>
        val move = list(Random.nextInt(list.length))
        Some(move)
      case _ =>
        None
    }

  }

  /*def onEnd() = {
    logger.info(
      "Mission completed\n" +
      "Path:\n" + path.reverse.mkString(" --> ")
    )
  }*/

}
