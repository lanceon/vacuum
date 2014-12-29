package bootstrap.liftweb

import net.liftweb.http.{Html5Properties, LiftRules, Req}
import net.liftweb.sitemap.{Menu, SiteMap}

class Boot {

  def boot {

    LiftRules.addToPackages("com.webitoria.vacuum")

    def sitemap(): SiteMap = SiteMap(
      Menu.i("Home") / "index"
    )

    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    {
      import reactive.web.lift._
      LiftCometTransportType.init()
      AppendToRender.init()
      SimpleAjaxTransportType.init()
      SseTransportType.init()
    }

  }

}
