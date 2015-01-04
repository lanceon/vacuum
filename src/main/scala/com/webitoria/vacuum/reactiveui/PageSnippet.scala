package com.webitoria.vacuum.reactiveui

import com.webitoria.vacuum.reactivehelpers.MySseTransportType
import reactive.Observing
import reactive.web.Page
import reactive.web.lift._

trait PageSnippet extends Observing {

  implicit lazy val page = Page(
    new AppendToRenderTransportType(_),
    //new SimpleAjaxTransportType(_)
    new MySseTransportType(_)
    //new LiftCometTransportType(_)
  )

}
