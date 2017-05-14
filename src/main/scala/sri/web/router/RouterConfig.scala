package sri.web.router

import sri.core.{React, ReactElement}

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.ConstructorTag

abstract class RouterConfig extends RouteDefinitions with PathUtils {

  private[router] val staticRoutes: js.Dictionary[Route] = js.Dictionary()

  private[router] val dynamicRoutes: js.Dictionary[Route] = js.Dictionary()

  val history: History

  val initialRoute: (String, Route)

  def registerInitialScreen[C <: RouterScreenClass: ConstructorTag](
      implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenName[C]
    val r = screenKey -> Route(path = "/",
                               component = js.constructorTag[C].constructor,
                               screenKey = screenKey)
    staticRoutes += r
    r
  }

  def registerScreen[C <: RouterScreenClass { type Params = Null }: ConstructorTag](
      path: String)(implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenName[C]
    val p = prefixSlashAndRemoveTrailingSlashes(path)
    staticRoutes(screenKey) = Route(path = p,
                                    component =
                                      js.constructorTag[C].constructor,
                                    screenKey = screenKey)
  }

  def registerDynamicScreen[C <: RouterScreenClass {
    type Params >: Null <: js.Object
  }: ConstructorTag](path: String)(implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenName[C]
    val p =
      prefixSlashAndRemoveTrailingSlashes(path)
    val keys: js.Array[PathRegExpKey] = js.Array()
    val pathRegexP = PathToRegexP(p, keys)
    val toPath = PathToRegexP.compile(p)
    dynamicRoutes(screenKey) = Route(path = p,
                                     component =
                                       js.constructorTag[C].constructor,
                                     keys = keys,
                                     toPath = toPath,
                                     pathRegexP = pathRegexP,
                                     screenKey = screenKey)
  }

  def registerModule(moduleConfig: RouterModuleConfig) = {
    moduleConfig.staticRoutes.foreach {
      case (screenKey, route) => {
        staticRoutes(screenKey) = route
      }
    }

    moduleConfig.dynamicRoutes.foreach {
      case (screenKey, route) => {
        val keys: js.Array[PathRegExpKey] = js.Array()
        val pathRegexP = PathToRegexP(route.path, keys)
        val toPath = PathToRegexP.compile(route.path)
        dynamicRoutes(screenKey) =
          route.copy(keys = keys, toPath = toPath, pathRegexP = pathRegexP)
      }
    }
    moduleConfig.staticRoutes.clear()
    moduleConfig.dynamicRoutes.clear()
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
