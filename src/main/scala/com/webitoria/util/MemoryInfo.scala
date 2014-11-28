package com.webitoria.util

/**
 * Created by Alexey.Zavalin on 29.11.2014.
 */
object MemoryInfo {

  def get: String = {
    val mb = 1024*1024
    s"Memory stats  - used: ${ (runtime.totalMemory() - runtime.freeMemory()) / mb } Mb, " +
    s"Free: ${ runtime.freeMemory() / mb } Mb, " +
    s"Total: ${ runtime.totalMemory() / mb } Mb, " +
    s"Max: ${ runtime.maxMemory() / mb } Mb"
  }

  private lazy val runtime: Runtime = Runtime.getRuntime()

}
