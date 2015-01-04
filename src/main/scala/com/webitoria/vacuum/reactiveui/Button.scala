package com.webitoria.vacuum.reactiveui

import com.webitoria.util.Loggable
import reactive.web.html.Span
import reactive.{Signal, Observing}
import reactive.web.{DomEventSource, RElem}
import reactive.web._
import reactive.web.javascript._

import scala.xml.Elem

class Button[T](name: String)(onClick: => T = Unit)(implicit val obs: Observing, page: Page) extends RElem with Loggable {

  val clickEventSource = DomEventSource.click
  override def events = Seq(clickEventSource)
  override def baseElem: Elem = <button type="button" class="btn btn-default">{ name }</button>
  override def properties = Nil
  clickEventSource.eventStream ->> {
    logger.info(s"Button [$name] clicked")
    onClick
  }
}

object Button {

  def apply[T](name: String)(onClick: => T)(implicit obs: Observing, page: Page) = {
    new Button(name)(onClick)
  }

}







