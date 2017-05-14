package sri.web.router

import sri.core._
import sri.universal._

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportStatic, ScalaJSDefined}
import scala.scalajs.js.|

sealed trait WebPage

case class Route(screenKey: String,
                 component: js.Any,
                 path: String,
                 params: js.UndefOr[js.Object] = js.undefined,
                 state: js.UndefOr[Any] = js.undefined,
                 keys: js.UndefOr[js.Array[PathRegExpKey]] = js.undefined,
                 pathRegexP: js.UndefOr[PathRegexp] = js.undefined,
                 toPath: js.UndefOr[PathFunction] = js.undefined,
                 search: js.UndefOr[String] = js.undefined,
                 action: js.UndefOr[String] = js.undefined)

@ScalaJSDefined
class RouterContext extends ComponentP[Router.State] {

  def render() = {
    props.location != null ?= {
      RouteUtils.setCurrentRoute(props.location, props.ctrl)
      props.ctrl.config
        .renderScene(props.ctrl)
    }
  }

  def getChildContext() = {
    js.Dictionary("routerctrl" -> props.ctrl)
  }
}

object RouterContext {

  @JSExportStatic
  val childContextTypes = contextTypes

  case class Props(ctrl: RouterCtrl)

  def apply(props: Router.State,
            key: String | Int = null,
            ref: js.Function1[RouterContext, Unit] = null) =
    CreateElement[RouterContext](props, key = key, ref = ref)

}

@ScalaJSDefined
class Router extends Component[Router.Props, Router.State] {
  import Router._

  override def componentWillMount(): Unit = {
    val history = props.config.history
    val ctrl = new RouterCtrl(history, props.config)
    initialState(State(ctrl, history.location))
    unlisten = history.listen((loc: Location, action: String) => {
      setState((state: State) => state.copy(location = loc))
    })

  }

  override def render(): ReactElement = {
    RouterContext(state)
  }

  override def componentWillUnmount(): Unit = {
    if (unlisten != null) unlisten()
  }

  var unlisten: js.Function0[_] = null

}
object Router {

  case class State(ctrl: RouterCtrl, location: Location)

  case class Props(config: RouterConfig)

  def apply(routerConfig: RouterConfig) =
    CreateElement[Router](Props(routerConfig))

}
