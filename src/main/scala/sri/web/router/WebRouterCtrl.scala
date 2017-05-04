package sri.web.router

import scala.reflect.ClassTag
import scala.scalajs.js

final class WebRouterCtrl[P <: js.Object] private[router] (
    val history: History,
    val config: WebRouterConfig) {

  private[router] var _currentRoute: WebRoute[P] =
    config.initialRoute._2.asInstanceOf[WebRoute[P]]

  private[router] var _previousRoute: js.UndefOr[WebRoute[js.Object]] =
    js.undefined

  def currentRoute = _currentRoute

  def previousRoute = _previousRoute

  /**
    * use this method to navigate to static pages ,it pushes new scene to the stack
    */
  def navigate[C <: WebRouterScreenClass: js.ConstructorTag](
      action: WebNavigationAction = WebNavigationAction.PUSH,
      search: js.UndefOr[String] = js.undefined,
      params: js.UndefOr[C#Params] = js.undefined)(
      implicit ctag: ClassTag[C]) = {
    val page = getWebRouterScreenName[C]
    config.staticRoutes.get(page) match {
      case Some(route) => {
        val location =
          new Location(pathname = route.path, search = search)
        if (action == WebNavigationAction.REPLACE) history.replace(location)
        else history.push(location)
      }
      case None => handleNotFound()
    }
  }

//  def navigateToDynamic[T](page: WebDynamicPage[T],
//                           placeholder: String,
//                           action: WebNavigationAction =
//                             WebNavigationAction.PUSH,
//                           search: js.UndefOr[String] = js.undefined,
//                           state: js.UndefOr[js.Object] = js.undefined) =
//    config.dynamicRoutes.get(page) match {
//      case Some(route) => {
//        val location = new Location(
//          pathname = s"${route.path}${placeholder.removeForwardSlashes}",
//          search = search,
//          state = state)
//        if (action == WebNavigationAction.REPLACE) history.replace(location)
//        else history.push(location)
//      }
//      case None => handleNotFound()
//    }

  def navigateBack() = history.goBack()

  def navigateForward() = history.goForward()

  private def handleNotFound() = {
    val location = new Location(
      pathname = config.staticRoutes
        .getOrElse(config.notFound.page, config.initialRoute._2)
        .path)
    if (config.notFound.action == WebNavigationAction.REPLACE)
      history.replace(location)
    else history.push(location)
  }

}
