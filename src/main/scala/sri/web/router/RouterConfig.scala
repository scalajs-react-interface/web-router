package sri.web.router

import sri.core.{React, ReactElement}

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.ConstructorTag

abstract class RouterConfig extends RouteDefinitions {

  private[router] var staticRoutes: Map[String, Route] = Map()

  private[router] var dynamicRoutes: Map[String, Route] = Map()

  lazy val staticRoutesByPath: Map[String, Route] =
    staticRoutes.map {
      case (k, v) => (v.path, v)
    }

  val history: History

  val initialRoute: (String, Route)

  def registerInitialScreen[C <: RouterScreenComponent[_, _]: ConstructorTag](
      implicit ctag: ClassTag[C]) = {
    val screenKey = getWebRouterScreenName[C]
    val r = screenKey -> Route(path = "/",
                               component = js.constructorTag[C].constructor,
                               screenKey = screenKey)
    staticRoutes += r
    r
  }

  def registerScreen[C <: RouterScreenComponent[Null, _]: ConstructorTag](
      path: String)(implicit ctag: ClassTag[C]) = {
    val page = getWebRouterScreenName[C]
    val p =
      if (path.startsWith("/")) s"/${path.tail.removeTrailingSlash}"
      else s"/${path.removeTrailingSlash}"
    staticRoutes += page -> Route(path = p,
                                  component = js.constructorTag[C].constructor,
                                  screenKey = page)
  }

  def registerDynamicScreen[
      C <: RouterScreenComponent[_ <: js.Object, _]: ConstructorTag](
      path: String)(implicit ctag: ClassTag[C]) = {
    val screenKey = getWebRouterScreenName[C]
    val p =
      if (path.startsWith("/")) s"/${path.tail.removeTrailingSlash}"
      else s"/${path.removeTrailingSlash}"
    val keys: js.Array[PathRegExpKey] = js.Array()
    val pathRegexP = PathToRegexP(p, keys)
    val toPath = PathToRegexP.compile(p)
    dynamicRoutes += screenKey -> Route(path = p,
                                        component =
                                          js.constructorTag[C].constructor,
                                        keys = keys,
                                        toPath = toPath,
                                        pathRegexP = pathRegexP,
                                        screenKey = screenKey)
  }

  /**
    * not found route
    * @return
    */
  val notFound: RouteNotFound

  /**
    * this method is responsible for rendering components ,
    * @return
    */
  def renderScene(ctrl: RouterCtrl): ReactElement = {
    React.createElement(ctrl.currentRoute.component,
                        new RouterScreenProps[js.Object] {
                          override val navigation: RouterCtrl =
                            ctrl
                        })
  }
}
