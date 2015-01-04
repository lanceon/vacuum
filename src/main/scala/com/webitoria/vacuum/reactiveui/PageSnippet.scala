package com.webitoria.vacuum.reactiveui

import reactive.Observing
import reactive.web.Page
import reactive.web.lift._

trait PageSnippet extends Observing {

  implicit lazy val page = Page(
    new AppendToRenderTransportType(_),
    new SimpleAjaxTransportType(_),
    new SseTransportType(_)
    //new LiftCometTransportType(_)
  )

}
