package sri.web.router

import sri.core.{React, ReactElement}

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.ConstructorTag

abstract class RouterConfig extends PathUtils {

  private[router] val staticRoutes: js.Dictionary[Route] = js.Dictionary()

  private[router] val dynamicRoutes: js.Dictionary[Route] = js.Dictionary()

  private[router] var auth: RouterAuth = null

  val history: History

  def registerScreen[C <: RouterScreenClass { type Params = Null }: ConstructorTag](
      path: String,
      title: String = "",
      secured: Boolean = true)(implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenKey[C]
    val p =
      if (path == "/") path else prefixSlashAndRemoveTrailingSlashes(path)
    staticRoutes(screenKey.toString) = Route(
      path = p,
      title = title,
      secured = secured,
      component = js.constructorTag[C].constructor,
      screenKey = screenKey)
  }

  def registerDynamicScreen[C <: RouterScreenClass {
    type Params >: Null <: js.Object
  }: ConstructorTag](path: String,
                     secured: Boolean = true,
                     title: String = "")(implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenKey[C]
    val p =
      prefixSlashAndRemoveTrailingSlashes(path)
    val keys: js.Array[PathRegExpKey] = js.Array()
    val pathRegexP = PathToRegexP(p, keys)
    val toPath = PathToRegexP.compile(p)
    dynamicRoutes(screenKey.toString) = Route(
      path = p,
      component = js.constructorTag[C].constructor,
      keys = keys,
      secured = secured,
      toPath = toPath,
      title = title,
      pathRegexP = pathRegexP,
      screenKey = screenKey)
  }

  def registerAuthScreen[C <: RouterScreenClass { type Params = Null }: ConstructorTag](
      path: String,
      validator: () => Boolean,
      action: NavigationAction = NavigationAction.REPLACE,
      title: String = "")(implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenKey[C]
    val p = prefixSlashAndRemoveTrailingSlashes(path)
    staticRoutes(screenKey.toString) = Route(
      path = p,
      title = title,
      secured = false,
      component = js.constructorTag[C].constructor,
      screenKey = screenKey)
    auth =
      RouterAuth(screenKey = screenKey, validator = validator, action = action)
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
  def renderScene(navigation: RouterCtrl): ReactElement = {
    React.createElement(
      navigation.currentRoute.component,
      js.Dynamic.literal(navigation = navigation.asInstanceOf[js.Any]))
  }

}

case class RouterAuth(screenKey: RouterScreenKey,
                      validator: () => Boolean,
                      action: NavigationAction = NavigationAction.REPLACE)
