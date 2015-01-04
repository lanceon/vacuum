package com.webitoria.vacuum.snippet

import com.webitoria.util.Loggable
import com.webitoria.vacuum.Field._
import com.webitoria.vacuum._
import com.webitoria.vacuum.reactiveui.{Button, PageSnippet}
import reactive.web._
import reactive.web.html.Span
import reactive.{Timer, Var}

import scala.xml.{Text, NodeSeq}

class SimSnippet extends PageSnippet with Loggable {

  //val robot = new Robot("Robot-1", field, startPos)

  //val timer = new Timer(0, 300)
  //val robotPos = Var[Pos](startPos)

  /*val sim = new Simulation(field, robot, moveLimit = 1000, timer, startPos)
  sim.eventStream =>> { state =>
    view.updateState(state)
    logger.info("sim state changed: " + state.pos.toString)
    robotPos.update(state.pos)
  }
*/
  //sim.subscribe(state => view.updateState(state))

  //sim.subscribe(state => logger.info(MemoryInfo.get))

  //val btnStop = Button("Stop"){ sim.stop() }


  val emptyField = new EmptyFieldNoGarbage(logger)
  val simpleField = new SimpleField(logger)


  val startPos = Pos(0,0)

  val view = new ReactiveView(simpleField)

  val btnStart = Button("Start"){  }

  def render: NodeSeq => NodeSeq = {
    import net.liftweb.util.BindHelpers._
    //"data-bind=stop" #> btnStop &
    "data-bind=start" #> btnStart &
    "data-bind=table" #> { (ns:NodeSeq) => view.render }
    //"data-bind=position" #> Span(robotPos.map(p => Text(p.toString)))
  }

}
