package sri.web.router

import sri.core._

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
trait RouterScreenClass extends ReactClass {
  type Params <: js.Object
  type LocationState <: AnyRef
}

@ScalaJSDefined
trait RouterScreenProps[Params <: js.Object] extends js.Object {
  val navigation: RouterCtrl
}

/**
  *
  * @tparam P
  */
@ScalaJSDefined
abstract class RouterScreenComponentP[P >: Null <: js.Object]
    extends RouterScreenComponent[P, Null, Null] {

  @inline
  def params: P =
    navigation.currentRoute.params.getOrElse(null).asInstanceOf[P]
}

@ScalaJSDefined
abstract class RouterScreenComponentLS[LS >: Null <: AnyRef]
    extends RouterScreenComponent[Null, Null, LS] {

  @inline
  def locationState: LS =
    navigation.currentRoute.state.getOrElse(null).asInstanceOf[LS]
}

@ScalaJSDefined
abstract class RouterScreenComponentSLS[
    S >: Null <: AnyRef, LS >: Null <: AnyRef]
    extends RouterScreenComponent[Null, S, LS] {

  @inline
  def locationState: LS =
    navigation.currentRoute.state.getOrElse(null).asInstanceOf[LS]
}

@ScalaJSDefined
abstract class RouterScreenComponentPLS[
    P >: Null <: js.Object, LS >: Null <: AnyRef]
    extends RouterScreenComponent[P, Null, LS] {

  @inline
  def params: P = navigation.currentRoute.params.get.asInstanceOf[P]

  @inline
  def locationState: LS = navigation.currentRoute.state.get.asInstanceOf[LS]
}

@ScalaJSDefined
abstract class RouterScreenComponentPS[
    P >: Null <: js.Object, S >: Null <: AnyRef]
    extends RouterScreenComponent[P, S, Null] {

  @inline
  def params: P = navigation.currentRoute.params.get.asInstanceOf[P]

}

@ScalaJSDefined
abstract class RouterScreenComponentNoPSLS
    extends RouterScreenComponent[Null, Null, Null]
@ScalaJSDefined
abstract class RouterScreenComponentS[S >: Null <: AnyRef]
    extends RouterScreenComponent[Null, S, Null]

@ScalaJSDefined
abstract class RouterScreenComponent[P >: Nothing <: js.Object, S <: AnyRef,
LS <: AnyRef](implicit ev: =:!=[P, Nothing])
    extends ComponentJS[RouterScreenProps[P], S]
    with RouterScreenClass {
  override type Params = P
  override type LocationState = LS
  @inline def navigation = props.navigation
}

@ScalaJSDefined
abstract class RouterAwareComponent[P >: Null <: AnyRef, S >: Null <: AnyRef]
    extends Component[P, S] {

  @inline
  def navigation: RouterCtrl =
    context.routerctrl.asInstanceOf[RouterCtrl]

}

@ScalaJSDefined
abstract class RouterAwareComponentP[P >: Null <: AnyRef]
    extends ComponentP[P] {

  @inline
  def navigation: RouterCtrl =
    context.routerctrl.asInstanceOf[RouterCtrl]

}

@ScalaJSDefined
abstract class RouterAwareComponentS[S >: Null <: AnyRef]
    extends ComponentS[S] {

  @inline
  def navigation: RouterCtrl =
    context.routerctrl.asInstanceOf[RouterCtrl]

}

@ScalaJSDefined
abstract class RouterAwareComponentJS[
    P >: Null <: js.Object, S >: Null <: AnyRef]
    extends ComponentJS[P, S] {

  @inline
  def navigation: RouterCtrl =
    context.routerctrl.asInstanceOf[RouterCtrl]

}
