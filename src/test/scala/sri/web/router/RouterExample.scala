package sri.web.router

import sri.core.{React, ReactElement}

import scala.scalajs.js
import scala.scalajs.js.JSON
import scala.scalajs.js.annotation.ScalaJSDefined

object RouterExample {

  var routerCtrl: RouterCtrl = null
  object Config extends RouterConfig {
    override val history: History = History.createMemoryHistory(js.undefined)
    override val initialRoute: (String, Route) =
      registerInitialScreen[HomeScreen]

    registerScreen[StaticStateScreen]("staticsatte")

    /**
      * this method is responsible for rendering components ,
      *
      * @return
      */
    override def renderScene(ctrl: RouterCtrl): ReactElement = {
      routerCtrl = ctrl
      println(s"rendering current route : ${ctrl.currentRoute.screenKey}")
      super.renderScene(ctrl)
    }

    /**
      * not found route
      *
      * @return
      */
    override val notFound: RouteNotFound = RouteNotFound(initialRoute._1)
  }

  @ScalaJSDefined
  class HomeScreen extends RouterScreenComponentNoPS {

    def render() = {
      React.createElement("div", js.Dynamic.literal(), "home")
    }
  }

  @ScalaJSDefined
  class StaticStateScreen extends RouterScreenComponentNoPS {

    def render() = {
      println(s"dude scond screen : ${navigation.currentRoute.state}")
      null
    }
  }

}
