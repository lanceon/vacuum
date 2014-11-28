package com.webitoria.vacuum

import com.webitoria.util.Loggable
import reactive.{Observing, Timer}

/**
 * Created by Alexey.Zavalin on 28.11.2014.
 */
class Simulation(field: Field,
                robot: Robot,
                moveLimit: Int,
                tickMillis: Int,
                startPos: Pos = Pos(0,0)) extends Loggable {

  case class SimState(field: Field,
                      var score: Int = 0,
                      var pos: Pos = startPos,
                      var stopFlag: Boolean = false,
                      var moves: Int = 0)

  private case object Tick
  private val state = SimState(field)
  private val ticks = new Timer(0, tickMillis).map(_ => Tick).filter(_ => !state.stopFlag)
  private implicit val observing = new Observing { }

  def subscribe(listener: SimState => Unit): Unit =
    ticks += { _ => listener(state) }

  def start(): Unit = {

    logger.info("Starting...")

    state.moves = 0
    state.stopFlag = false
    state.score = 0

    subscribe(_ => {
      robot.onTick()
      state.moves = state.moves + 1
      state.pos = robot.getPos
      logger.info(s"Move #${state.moves} is completed")
    })

  }

  def stop() = {
    logger.info("Stopping...")
    state.stopFlag = true
  }

}
