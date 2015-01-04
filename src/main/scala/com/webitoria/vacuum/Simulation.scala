package com.webitoria.vacuum

import com.webitoria.util.Loggable
import reactive.web.DomEventSource
import reactive.{EventSource, Var, EventStream, Observing}


case class SimState(field: Field,
                    score: Int,
                    pos: Pos,
                    moves: Int)

object Once {

  def apply[T](event: T) = {
    val es = new EventSource[T]()
    es.fire(event)
    es
  }

}

class Simulation(field: Field,
                 robot: Robot,
                 moveLimit: Int,
                 timer: EventStream[Long],
                 startPos: Pos)(implicit val obs: Observing) extends Loggable {

  val startState = SimState(field, 0, startPos, 0)

  val eventStream = Once(startState) | timer.map{ time =>
    robot.onTick()
    SimState(field, 0, robot.getPos, 0)
  }

}
