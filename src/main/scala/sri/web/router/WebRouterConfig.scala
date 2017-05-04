package sri.web.router

import sri.core.{React, ReactElement}

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.ConstructorTag

abstract class WebRouterConfig extends WebRouteDefinitions with PathUtils {

  private[router] var staticRoutes: Map[String, WebRoute[js.Object]] = Map()

//  private var _dynamicRoutes: Map[WebDynamicPage[_], WebRoute[js.Object]] = Map()

//  lazy val staticRoutes: Map[String, WebRoute[js.Object]] =
//    _staticRoutes.+(initialRoute)

//  lazy val dynamicRoutes: Map[WebDynamicPage[_], WebRoute[js.Object]] = _dynamicRoutes

  lazy val staticRoutesByPath: Map[String, WebRoute[js.Object]] =
    staticRoutes.map {
      case (k, v) => (v.path, v)
    }

  val history: History

  val initialRoute: (String, WebRoute[js.Object])

  def registerInitialScreen[
      C <: WebRouterScreenComponent[_, _]: ConstructorTag](
      implicit ctag: ClassTag[C]) = {
    val page = getWebRouterScreenName[C]
    val r = page -> WebRoute[js.Object](path = "/",
                                        component =
                                          js.constructorTag[C].constructor,
                                        page = page)
    staticRoutes += r
    r
  }

  def registerScreen[C <: WebRouterScreenComponent[_, _]: ConstructorTag](
      path: String)(implicit ctag: ClassTag[C]) = {
    val page = getWebRouterScreenName[C]
    staticRoutes += page -> WebRoute[js.Object](
      path = createStaticPath(path),
      component = js.constructorTag[C].constructor,
      page = page)
  }

  /**
    * not found route
    * @return
    */
  val notFound: WebRouteNotFound

  /**
    * this method is responsible for rendering components ,
    * @param route current route that is pushed to stack
    * @return
    */
  def renderScene(ctrl: WebRouterCtrl[js.Object]): ReactElement = {
    React.createElement(ctrl.currentRoute.component,
                        new WebRouterScreenProps[js.Object] {
                          override val navigation: WebRouterCtrl[js.Object] =
                            ctrl
                        })
  }
}
