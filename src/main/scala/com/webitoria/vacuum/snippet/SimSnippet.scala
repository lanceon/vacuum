package com.webitoria.vacuum.snippet

import com.webitoria.util.Loggable
import com.webitoria.vacuum._
import com.webitoria.vacuum.reactiveui.{Button, PageSnippet}
import reactive.web.html.Span
import reactive.{Timer, Var}
import net.liftweb.util.BindHelpers._

import scala.xml.{Text, NodeSeq}

class SimSnippet extends PageSnippet with Loggable {

  val emptyField = new EmptyFieldNoGarbage(logger)
  val simpleField = new SimpleField(logger)

  val robotPos = Var[Pos](Pos(0,0))

  val robot = new Robot("Robot-1", simpleField, logger)
  val timer = new Timer(0, 300)
  val sim = new Simulation(simpleField, robot, robotPos, timer, logger)
  val view = new ReactiveView(simpleField, sim.robotPos)

  val btnStart = Button("Start"){  }
  val btnStop = Button("Stop"){  }

  def render: NodeSeq => NodeSeq = {
    "data-bind=stop" #> btnStop &
    "data-bind=start" #> btnStart &
    "data-bind=table" #> { (ns:NodeSeq) => view.render } &
    "data-bind=position" #> Span(robotPos.map(p => Text(p.toString)))
  }

}
