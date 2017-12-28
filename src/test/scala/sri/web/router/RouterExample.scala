package sri.web.router

import org.scalajs.dom
import sri.core.{
  CreateElement,
  JSProps,
  React,
  ReactElement,
  ReactScalaClass
}

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.{JSImport, JSName}

object RouterExample {
// for testing purpose
  var navigation: RouterCtrl = null
  object Config extends RouterConfig {
    override val history: History = History.createMemoryHistory(js.undefined)
    registerScreen[HomeScreen]("/")
    registerScreen[StaticSecondScreen]("staticsatte")

    /**
      * this method is responsible for rendering components ,
      *
      * @return
      */
    override def renderScene(ctrl: RouterCtrl): ReactElement = {
      navigation = ctrl
      super.renderScene(ctrl)
    }

    /**
      * not found route
      *
      * @return
      */
    override val notFound: RouteNotFound = RouteNotFound(
      getRouterScreenKey[HomeScreen])
  }

  class HomeScreen extends RouterScreenComponentNoPSLS {

    def render() = {
      React.createElement("div", js.Dynamic.literal(), "home")
    }
  }

  class StaticSecondScreen extends RouterScreenComponentNoPSLS {

    def render() = {
      React.createElement("div", js.Dynamic.literal(), "second")
    }

  }

}
//case class SecondaryProps(name: String)
//
//trait SecondaryProps extends js.Object {
//  val name: String
//}
//
//class ExampleSecondary(val initialProps: SecondaryProps)
//    extends ReactSecondary(initialProps) {
//
//  def render() = {
//    println(s"Props : ${JSON.stringify(props.asInstanceOf[js.Any])}")
//    println(s"JSProps : ${JSON.stringify(jsProps)}")
////    println(s"jsProps : ${JSON.stringify(jsProps)}")
//    React.createElement("div", js.Dynamic.literal(), s"second")
//  }
//}
//
//object ExampleSecondary {
//
//  def apply(props: SecondaryProps) = CreateElement[ExampleSecondary](props)
//}
