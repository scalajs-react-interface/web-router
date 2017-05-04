package sri.web.router

case class WebRouteNotFound(page: String,
                            action: WebNavigationAction =
                              WebNavigationAction.REPLACE)
