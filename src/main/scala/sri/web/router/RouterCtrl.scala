package sri.web.router

import scala.reflect.ClassTag
import scala.scalajs.js

final class RouterCtrl private[router] (val history: History,
                                        val config: RouterConfig) {

  private[router] var _currentRoute: Route =
    config.initialRoute._2.asInstanceOf[Route]

  private[router] var _previousRoute: js.UndefOr[Route] =
    js.undefined

  def currentRoute = _currentRoute

  def previousRoute = _previousRoute

  /**
    * use this method to navigate to static pages ,it pushes new scene to the stack
    */
  def navigate[C <: RouterScreenClass: js.ConstructorTag](
      action: NavigationAction = NavigationAction.PUSH,
      search: js.UndefOr[String] = js.undefined,
      state: js.UndefOr[js.Object] = js.undefined)(
      implicit ctag: ClassTag[C]) = {
    val screenKey = getWebRouterScreenName[C]
    config.staticRoutes.get(screenKey) match {
      case Some(route) => {
        val location =
          new Location(pathname = route.path, search = search, state = state)
        if (action == NavigationAction.REPLACE) history.replace(location)
        else history.push(location)
      }
      case None => handleNotFound()
    }
  }

  def navigateDynamic[C <: RouterScreenClass { type Params <: js.Object }: js.ConstructorTag](
      action: NavigationAction = NavigationAction.PUSH,
      search: js.UndefOr[String] = js.undefined,
      state: js.UndefOr[js.Object] = js.undefined,
      params: js.UndefOr[C#Params] = js.undefined)(
      implicit ctag: ClassTag[C]) = {
    val screenKey = getWebRouterScreenName[C]
    config.dynamicRoutes.get(screenKey) match {
      case Some(route) => {
        val location =
          new Location(pathname = route.toPath.get(params),
                       search = search,
                       state = state)
        if (action == NavigationAction.REPLACE) history.replace(location)
        else history.push(location)
      }
      case None => handleNotFound()
    }
  }

  def navigateBack() = history.goBack()

  def navigateForward() = history.goForward()

  private def handleNotFound() = {
    val location = new Location(
      pathname = config.staticRoutes
        .getOrElse(config.notFound.page, config.initialRoute._2)
        .path)
    if (config.notFound.action == NavigationAction.REPLACE)
      history.replace(location)
    else history.push(location)
  }

}
