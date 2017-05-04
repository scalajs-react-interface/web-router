package sri.web.router

import sri.core._
import sri.universal._

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportStatic, ScalaJSDefined}
import scala.scalajs.js.|

sealed trait WebPage

trait WebStaticPage extends WebPage

trait WebDynamicPage[T] extends WebPage

case class WebRoute[P <: js.Object](
    page: String,
    component: js.Any,
    path: String,
    parser: js.UndefOr[String => _] = js.undefined,
    placeholder: js.UndefOr[String] = js.undefined,
    params: js.UndefOr[P] = js.undefined,
    state: js.UndefOr[js.Object] = js.undefined,
    search: js.UndefOr[String] = js.undefined,
    action: js.UndefOr[String] = js.undefined)

@ScalaJSDefined
class WebRouterContext extends ComponentP[WebRouter.State] {

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

object WebRouterContext {

  @JSExportStatic
  val childContextTypes = routerContextTypes

  case class Props(ctrl: WebRouterCtrl[js.Object])

  def apply(props: WebRouter.State,
            key: String | Int = null,
            ref: js.Function1[WebRouterContext, Unit] = null) =
    CreateElement[WebRouterContext](props, key = key, ref = ref)

}

@ScalaJSDefined
class WebRouter extends Component[WebRouter.Props, WebRouter.State] {
  import WebRouter._

  override def componentWillMount(): Unit = {
    val history = props.config.history
    val ctrl = new WebRouterCtrl[js.Object](history, props.config)
    initialState(State(ctrl, history.location))
    unlisten = history.listen((loc: Location, action: String) => {
      setState((state: State) => state.copy(location = loc))
    })

  }

  override def render(): ReactElement = {
    WebRouterContext(state)
  }

  override def componentWillUnmount(): Unit = {
    if (unlisten != null) unlisten()
  }

  var unlisten: js.Function0[_] = null

}
object WebRouter {

  case class State(ctrl: WebRouterCtrl[js.Object], location: Location)

  case class Props(config: WebRouterConfig)

  def apply(routerConfig: WebRouterConfig) =
    CreateElement[WebRouter](Props(routerConfig))

}
