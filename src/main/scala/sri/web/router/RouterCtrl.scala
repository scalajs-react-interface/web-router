package sri.web.router

import scala.reflect.ClassTag
import scala.scalajs.js

final class RouterCtrl private[router] (val history: History,
                                        val config: RouterConfig) {

  private[router] var _currentRoute: Route =
    null

  private[router] var _previousRoute: js.UndefOr[Route] =
    js.undefined

  def currentRoute = _currentRoute

  def previousRoute = _previousRoute

  def navigate[C <: RouterScreenClass { type Params = Null }: js.ConstructorTag](
      aaction: NavigationAction = NavigationAction.PUSH,
      search: js.UndefOr[String] = js.undefined)(implicit ctag: ClassTag[C]) =
    navigateStatic[C](action = aaction, search = search)

  def navigateLS[C <: RouterScreenClass {
    type Params = Null; type LocationState >: Null <: AnyRef
  }: js.ConstructorTag](
      state: C#LocationState,
      aaction: NavigationAction = NavigationAction.PUSH,
      search: js.UndefOr[String] = js.undefined)(implicit ctag: ClassTag[C]) =
    navigateStatic[C](action = aaction, search = search, state = state)

  def navigateP[C <: RouterScreenClass { type Params >: Null <: js.Object }: js.ConstructorTag](
      params: C#Params,
      aaction: NavigationAction = NavigationAction.PUSH,
      search: js.UndefOr[String] = js.undefined)(implicit ctag: ClassTag[C]) =
    navigateDynamic[C](params = params, action = aaction, search = search)

  def navigatePLS[C <: RouterScreenClass {
    type Params >: Null <: js.Object; type LocationState >: Null <: AnyRef
  }: js.ConstructorTag](
      params: C#Params,
      state: C#LocationState,
      action: NavigationAction = NavigationAction.PUSH,
      search: js.UndefOr[String] = js.undefined)(implicit ctag: ClassTag[C]) =
    navigateDynamic[C](params = params,
                       action = action,
                       search = search,
                       state = state)

  /**
    * use this method to navigate to static pages ,it pushes new scene to the stack
    */
  private def navigateStatic[C <: RouterScreenClass { type Params = Null }: js.ConstructorTag](
      action: NavigationAction = NavigationAction.PUSH,
      search: js.UndefOr[String] = js.undefined,
      state: js.UndefOr[C#LocationState] = js.undefined)(
      implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenKey[C]
    config.staticRoutes.get(screenKey.toString) match {
      case Some(route) => {
        val location =
          new Location(pathname = route.path, search = search, state = state)
        if (action == NavigationAction.REPLACE) history.replace(location)
        else history.push(location)
      }
      case None => handleNotFound()
    }
  }

  private def navigateDynamic[C <: RouterScreenClass {
    type Params >: Null <: js.Object
  }: js.ConstructorTag](action: NavigationAction = NavigationAction.PUSH,
                        search: js.UndefOr[String] = js.undefined,
                        state: js.UndefOr[C#LocationState] = js.undefined,
                        params: js.UndefOr[C#Params] = js.undefined)(
      implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenKey[C]
    config.dynamicRoutes.get(screenKey.toString) match {
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

  /**
    * use this method to navigate to static pages using screenKey
    */
  def navigateWithKey(screenKey: RouterScreenKey,
                      action: NavigationAction = NavigationAction.PUSH,
                      search: js.UndefOr[String] = js.undefined,
  ) = {
    config.staticRoutes.get(screenKey.toString) match {
      case Some(route) => {
        val location =
          new Location(pathname = route.path, search = search)
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
        .getOrElse(config.notFound.page.toString, config.staticRoutes.head._2)
        .path)
    if (config.notFound.action == NavigationAction.REPLACE)
      history.replace(location)
    else history.push(location)
  }

}
