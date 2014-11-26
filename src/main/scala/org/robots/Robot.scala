package org.robots

import org.Pos
import org.util.Loggable

/**
 * Created by Alexey.Zavalin on 26.11.2014.
 */
class Robot extends Loggable {

  val name = "Vac-1"

  var path: List[Pos] = Nil

  def onTick() = {
    logger.info(s"$name: handling tick")
  }

  def onEnd() = {
    logger.info(
      "Mission completed\n" +
      "Path:\n" + path.reverse.mkString(" --> ")
    )
  }

}
