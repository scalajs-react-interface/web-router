package sri.web.router

import sri.core._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportStatic

trait RouterScreenClass extends ReactClass {
  type Params <: js.Object
  type LocationState <: AnyRef
}

trait RouterScreenProps[Params <: js.Object] extends js.Object {
  val navigation: RouterCtrl
}

/**
  *
  * @tparam P
  */
abstract class RouterScreenComponentP[P >: Null <: js.Object]
    extends RouterScreenComponent[P, Null, Null] {

  @inline
  def params: P =
    navigation.currentRoute.params.getOrElse(null).asInstanceOf[P]
}

abstract class RouterScreenComponentLS[LS >: Null <: AnyRef]
    extends RouterScreenComponent[Null, Null, LS] {

  @inline
  def locationState: js.UndefOr[LS] =
    navigation.currentRoute.state.asInstanceOf[js.UndefOr[LS]]
}

abstract class RouterScreenComponentSLS[
    S >: Null <: AnyRef, LS >: Null <: AnyRef]
    extends RouterScreenComponent[Null, S, LS] {

  @inline
  def locationState: js.UndefOr[LS] =
    navigation.currentRoute.state.asInstanceOf[js.UndefOr[LS]]
}

abstract class RouterScreenComponentPLS[
    P >: Null <: js.Object, LS >: Null <: AnyRef]
    extends RouterScreenComponent[P, Null, LS] {

  @inline
  def params: P = navigation.currentRoute.params.get.asInstanceOf[P]

  @inline
  def locationState: js.UndefOr[LS] =
    navigation.currentRoute.state.asInstanceOf[js.UndefOr[LS]]
}

abstract class RouterScreenComponentPS[
    P >: Null <: js.Object, S >: Null <: AnyRef]
    extends RouterScreenComponent[P, S, Null] {

  @inline
  def params: P = navigation.currentRoute.params.get.asInstanceOf[P]

}

abstract class RouterScreenComponentNoPSLS
    extends RouterScreenComponent[Null, Null, Null]
abstract class RouterScreenComponentS[S >: Null <: AnyRef]
    extends RouterScreenComponent[Null, S, Null]

abstract class RouterScreenComponent[P >: Nothing <: js.Object, S <: AnyRef,
LS <: AnyRef](implicit ev: =:!=[P, Nothing])
    extends ComponentJS[RouterScreenProps[P], S]
    with RouterScreenClass {
  override type Params = P
  override type LocationState = LS
  @inline def navigation = props.navigation
}

sealed trait RouterAwareComponentClass extends ReactScalaClass {
  override type ScalaPropsType <: AnyRef
}

abstract class RouterAwareComponent[P >: Null <: AnyRef, S >: Null <: AnyRef]
    extends Component[P, S]
    with RouterAwareComponentClass {

  @inline
  def navigation: RouterCtrl =
    jsProps.asInstanceOf[js.Dynamic].navigation.asInstanceOf[RouterCtrl]

}

abstract class RouterAwareComponentP[P >: Null <: AnyRef]
    extends ComponentP[P]
    with RouterAwareComponentClass {

  @inline
  def navigation: RouterCtrl =
    jsProps.asInstanceOf[js.Dynamic].navigation.asInstanceOf[RouterCtrl]

}

abstract class RouterAwareComponentS[S >: Null <: AnyRef]
    extends ComponentS[S]
    with RouterAwareComponentClass {

  @inline
  def navigation: RouterCtrl =
    jsProps.asInstanceOf[js.Dynamic].navigation.asInstanceOf[RouterCtrl]

}

abstract class RouterAwareComponentNoPS
    extends ComponentNoPS
    with RouterAwareComponentClass {

  @inline
  def navigation: RouterCtrl =
    jsProps.asInstanceOf[js.Dynamic].navigation.asInstanceOf[RouterCtrl]

}

abstract class RouterAwareComponentJS[
    P >: Null <: js.Object, S >: Null <: AnyRef]
    extends ComponentJS[P, S] {

  @inline
  def navigation: RouterCtrl =
    props.asInstanceOf[js.Dynamic].navigation.asInstanceOf[RouterCtrl]

}
class WithRouter extends ComponentP[WithRouter.Props] {

  def render() = {
    React.createElement(props.ctor,
                        js.Dynamic.literal(scalaProps =
                                             props.cProps.asInstanceOf[js.Any],
                                           navigation = context.navigation))
  }
}

object WithRouter {

  @JSExportStatic
  val contextType = navigationContext

  case class Props(ctor: js.Any, cProps: Any)

  @inline
  def apply[C <: RouterAwareComponentClass {
    type ScalaPropsType >: Null <: AnyRef
  }: js.ConstructorTag](props: C#ScalaPropsType) = {
    val ctor = js.constructorTag[C].constructor
    CreateElement[WithRouter](Props(ctor, props))
  }

  @inline
  def apply[C <: RouterAwareComponentClass { type ScalaPropsType = Null }: js.ConstructorTag]() = {
    val ctor = js.constructorTag[C].constructor
    CreateElement[WithRouter](Props(ctor, null))
  }
}
