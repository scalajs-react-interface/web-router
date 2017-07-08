package sri.web.router

import sri.core._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportStatic
import scala.scalajs.js.|

sealed trait WebPage

@js.native
trait RouterScreenKey extends js.Object

case class Route(screenKey: RouterScreenKey,
                 component: js.Any,
                 path: String,
                 title: String = "",
                 secured: Boolean = false,
                 params: js.UndefOr[js.Object] = js.undefined,
                 state: js.UndefOr[Any] = js.undefined,
                 keys: js.UndefOr[js.Array[PathRegExpKey]] = js.undefined,
                 pathRegexP: js.UndefOr[PathRegexp] = js.undefined,
                 toPath: js.UndefOr[PathFunction] = js.undefined,
                 search: js.UndefOr[String] = js.undefined,
                 action: js.UndefOr[String] = js.undefined)

class RouterContext extends ComponentNotPureP[RouterCtrl] {

  def render() = {
    props.config
      .renderScene(props)
  }

  def getChildContext() = {
    js.Dictionary("navigation" -> props)
  }
}

object RouterContext {

  @JSExportStatic
  val childContextTypes = navigationContext

  case class Props(ctrl: RouterCtrl)

  def apply(props: RouterCtrl,
            key: String | Int = null,
            ref: js.Function1[RouterContext, Unit] = null) =
    CreateElement[RouterContext](props, key = key, ref = ref)

}

class Router extends ComponentP[Router.Props] {
  import Router._

  var ctrl: RouterCtrl = null

  override def componentWillMount(): Unit = {
    val history = props.config.history
    ctrl = new RouterCtrl(history, props.config)
    unlisten = history.listen((loc: Location, action: String) => {
      handleAuthAndSetCurrentRoute(loc, true)
    })
    handleAuthAndSetCurrentRoute(history.location)
  }

  def render() = {
    RouterContext(ctrl)
  }

  def handleAuthAndSetCurrentRoute(loc: Location,
                                   updateComponent: Boolean = false) = {
    val route = RouteUtils.getRouteFromLocation(loc, ctrl)
    if (ctrl.config.auth == null || !route.secured || (ctrl.config.auth
          .validator())) {
      if (ctrl._currentRoute != null && ctrl._currentRoute.action.isDefined)
        ctrl._previousRoute = ctrl.currentRoute
      ctrl._currentRoute = route
      if (updateComponent) forceUpdate() // in location change scenario update the component
    } else {
      val loginRoute = ctrl.config.staticRoutes
        .find {
          case (key, _) => key == ctrl.config.auth.screenKey
        }
        .map(_._2)
        .getOrElse(null) // come and talk to me if you have a use case for dynamic login url and yeah please register your login screen otherwise i will throw NPE(i am a bad guy you know)
      val location =
        new Location(pathname = loginRoute.path)
      if (ctrl.config.auth.action == NavigationAction.REPLACE)
        ctrl.config.history.replace(location)
      else ctrl.config.history.push(location)
    }
  }

  override def componentWillUnmount(): Unit = {
    if (unlisten != null) unlisten()
  }

  var unlisten: js.Function0[_] = null

}

object Router {

  case class Props(config: RouterConfig)

  @inline
  def apply(routerConfig: RouterConfig) =
    CreateElement[Router](Props(routerConfig))

}
