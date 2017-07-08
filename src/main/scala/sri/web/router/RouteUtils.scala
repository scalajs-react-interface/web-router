package sri.web.router

import sri.universal._

import scala.scalajs.js

object RouteUtils {

  def getRouteFromLocation(loc: Location, ctrl: RouterCtrl): Route = {
    var result: Route = null
    var paramsLocal: js.UndefOr[js.Object] = js.undefined
    // first check in static routes
    val pathname =
      if (loc.pathname == "/") "/" else loc.pathname.removeTrailingSlash
    val sr = ctrl.config.staticRoutes.find {
      case (sccrenKey, route) => route.path == pathname
    }
    if (sr.isDefined) {
      result = sr.get._2
    } else {
      var values: js.Array[String] = null
      val dr = ctrl.config.dynamicRoutes.find {
        case (pagekey, route) => {
          val m1 = route.pathRegexP.get.exec(pathname)
          if (m1.isDefinedAndNotNull) {
            values = m1.get
            true
          } else false
        }
      }
      if (dr.isDefined) {
        result = dr.get._2
        val pairs = result.keys.get.toList.zipWithIndex.map {
          case (key, index) => {
            (key.name, values(index + 1))
          }
        }
        paramsLocal = js.Dictionary(pairs: _*).asInstanceOf[js.Object]
      } else {
        result = ctrl.config.staticRoutes
          .getOrElse(ctrl.config.notFound.page.toString,
                     ctrl.config.staticRoutes.head._2)
      }
    }
    result.copy(action = loc.action,
                search = loc.search,
                state = loc.state,
                params = paramsLocal)
  }

}
