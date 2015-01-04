package com.webitoria.vacuum

import org.slf4j.Logger
import com.webitoria.vacuum.reactivehelpers._
import reactive.{Var, Signal, Observing, EventStream}


case class SimState(field: Field, pos: Pos)

class Simulation(field: Field,
                 robot: Robot,
                 val robotPos: Var[Pos],
                 timer: EventStream[Long],
                 logger: Logger)(implicit val obs: Observing) {

  val movements: EventStream[Pos] = timer.map{ time =>
    val cur = robotPos.now
    val next = robot.requestMove(cur)
    logger.debug(s"Tick $time: requesting move from $cur to $next")
    next
  }

  movements =>> { m =>
    logger.debug(s"----> Updating robot pos to $m")
    robotPos.update(m)
    logger.debug(s"----> After update: ${robotPos.now}")
  }

}
