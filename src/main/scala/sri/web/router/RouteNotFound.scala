package sri.web.router

case class RouteNotFound(page: String,
                         action: NavigationAction = NavigationAction.REPLACE)
