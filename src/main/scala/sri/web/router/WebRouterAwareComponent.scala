package sri.web.router

import sri.core._

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
trait WebRouterScreenClass extends ReactClass {
  type Params <: js.Object
}

@ScalaJSDefined
trait WebRouterScreenProps[Params <: js.Object] extends js.Object {
  val navigation: WebRouterCtrl[Params]
}

@ScalaJSDefined
abstract class WebRouterScreenComponentP[P >: Null <: js.Object]
    extends WebRouterScreenComponent[P, Null]

@ScalaJSDefined
abstract class WebRouterScreenComponentNoPS
    extends WebRouterScreenComponent[Null, Null]
@ScalaJSDefined
abstract class WebRouterScreenComponentS[S >: Null <: AnyRef]
    extends WebRouterScreenComponent[Null, S]

@ScalaJSDefined
abstract class WebRouterScreenComponent[P >: Nothing <: js.Object, S <: AnyRef](
    implicit ev: =:!=[P, Nothing])
    extends ComponentJS[WebRouterScreenProps[P], S]
    with WebRouterScreenClass {
  override type Params = P
  @inline def navigation = props.navigation
}

@ScalaJSDefined
abstract class WebRouterAwareComponent[
    P >: Null <: AnyRef, S >: Null <: AnyRef]
    extends Component[P, S] {

  @inline
  def navigation: WebRouterCtrl[js.Object] =
    context.routerctrl.asInstanceOf[WebRouterCtrl[js.Object]]

}

/**
  * router component for JS props type
  * //TODO why no trait support for @ScalaJSDefined too much copy paste :(
  * @tparam P
  * @tparam S
  */
@ScalaJSDefined
abstract class WebRouterAwareComponentJS[
    P >: Null <: js.Object, S >: Null <: AnyRef]
    extends ComponentJS[P, S] {

  @inline
  def navigation: WebRouterCtrl[js.Object] =
    context.routerctrl.asInstanceOf[WebRouterCtrl[js.Object]]

}
