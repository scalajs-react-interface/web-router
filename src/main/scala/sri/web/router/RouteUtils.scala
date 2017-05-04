package sri.web.router

import scala.scalajs.js

object RouteUtils {

  def setCurrentRoute(loc: Location, ctrl: WebRouterCtrl[js.Object]) = {
    var result: WebRoute[js.Object] = null
    var placeholder = ""
    // first check in static routes
    val sr = ctrl.config.staticRoutesByPath.get(
      if (loc.pathname != "/") loc.pathname.removeTrailingSlash
      else loc.pathname)
    if (sr.isDefined) {
      result = sr.get
    } else { // TODO Dynamic route handling
      result = ctrl.config.staticRoutes
        .getOrElse(ctrl.config.notFound.page, ctrl.config.initialRoute._2)
    }
    if (ctrl._currentRoute.action.isDefined)
      ctrl._previousRoute = ctrl.currentRoute
    result = result.copy(action = loc.action,
                         search = loc.search,
                         state = loc.state,
                         placeholder = placeholder)
    ctrl._currentRoute = result
  }

}
