package com.webitoria.vacuum.ui

import reactive.Observing
import reactive.web.Page
import reactive.web.lift._

/**
 * Created by User on 28.12.2014.
 */
trait ReactivePage extends Observing {

  implicit lazy val page = Page(
    new SseTransportType(_),
    new LiftCometTransportType(_),
    new AppendToRenderTransportType(_),
    new SimpleAjaxTransportType(_)
  )

}
