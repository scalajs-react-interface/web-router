package sri.web.router

import sri.universal._

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

object RouteUtils {

  def setCurrentRoute(loc: Location, ctrl: RouterCtrl) = {
    var result: Route = null
    var placeholder = ""
    var params: js.UndefOr[js.Object] = js.undefined
    // first check in static routes
    val pathname =
      if (loc.pathname == "/") "/" else loc.pathname.removeTrailingSlash
    println(s"pathname : $pathname")
    val sr = ctrl.config.staticRoutesByPath.get(pathname)
    if (sr.isDefined) {
      result = sr.get
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
        params = js.Dictionary(pairs: _*).asInstanceOf[js.Object]
      } else {
        result = ctrl.config.staticRoutes
          .getOrElse(ctrl.config.notFound.page, ctrl.config.initialRoute._2)
      }
    }
    if (ctrl._currentRoute.action.isDefined)
      ctrl._previousRoute = ctrl.currentRoute
    result = result.copy(action = loc.action,
                         search = loc.search,
                         state = loc.state,
                         params = params)
    ctrl._currentRoute = result
  }

}
