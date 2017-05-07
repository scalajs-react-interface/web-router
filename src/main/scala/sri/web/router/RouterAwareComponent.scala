package sri.web.router

import sri.core._

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
trait RouterScreenClass extends ReactClass {
  type Params <: js.Object
}

@ScalaJSDefined
trait RouterScreenProps[Params <: js.Object] extends js.Object {
  val navigation: RouterCtrl
}

@ScalaJSDefined
abstract class RouterScreenComponentP[P >: Null <: js.Object]
    extends RouterScreenComponent[P, Null]

@ScalaJSDefined
abstract class RouterScreenComponentNoPS
    extends RouterScreenComponent[Null, Null]
@ScalaJSDefined
abstract class RouterScreenComponentS[S >: Null <: AnyRef]
    extends RouterScreenComponent[Null, S]

@ScalaJSDefined
abstract class RouterScreenComponent[P >: Nothing <: js.Object, S <: AnyRef](
    implicit ev: =:!=[P, Nothing])
    extends ComponentJS[RouterScreenProps[P], S]
    with RouterScreenClass {
  override type Params = P
  @inline def navigation = props.navigation
}

@ScalaJSDefined
abstract class RouterAwareComponent[P >: Null <: AnyRef, S >: Null <: AnyRef]
    extends Component[P, S] {

  @inline
  def navigation: RouterCtrl =
    context.routerctrl.asInstanceOf[RouterCtrl]

}

/**
  * router component for JS props type
  * //TODO why no trait support for @ScalaJSDefined too much copy paste :(
  * @tparam P
  * @tparam S
  */
@ScalaJSDefined
abstract class RouterAwareComponentJS[
    P >: Null <: js.Object, S >: Null <: AnyRef]
    extends ComponentJS[P, S] {

  @inline
  def navigation: RouterCtrl =
    context.routerctrl.asInstanceOf[RouterCtrl]

}
