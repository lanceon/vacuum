package com.webitoria.vacuum.reactivehelpers

import reactive.EventSource

object Once {

  def apply[T](event: T) = {
    val es = new EventSource[T]()
    es.fire(event)
    es
  }

}
