package sri.web.router

case class RouteNotFound(page: RouterScreenKey,
                         action: NavigationAction = NavigationAction.REPLACE)
