package sri.web.router

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.ConstructorTag

abstract class RouterModuleConfig(val moduleName: String) extends PathUtils {

  private[router] val staticRoutes: js.Dictionary[Route] = js.Dictionary()

  private[router] val dynamicRoutes: js.Dictionary[Route] = js.Dictionary()

  def registerScreen[C <: RouterScreenClass { type Params = Null }: ConstructorTag](
      path: String,
      secured: Boolean = true,
      title: String = "")(implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenKey[C]
    val p = "/" + moduleName + prefixSlashAndRemoveTrailingSlashes(path)
    staticRoutes(screenKey.toString) = Route(path = p,
                                             secured = secured,
                                             component =
                                               js.constructorTag[C].constructor,
                                             title = title,
                                             screenKey = screenKey)
  }

  def registerDynamicScreen[C <: RouterScreenClass {
    type Params >: Null <: js.Object
  }: ConstructorTag](path: String, secured: Boolean = true, title: String = "")(
      implicit ctag: ClassTag[C]) = {
    val screenKey = getRouterScreenKey[C]
    val p =
      prefixSlashAndRemoveTrailingSlashes(moduleName) + prefixSlashAndRemoveTrailingSlashes(
        path)
    dynamicRoutes(screenKey.toString) = Route(
      path = p,
      secured = secured,
      title = title,
      component = js.constructorTag[C].constructor,
      screenKey = screenKey)
  }

  def registerModule(moduleConfig: RouterModuleConfig) = {
    moduleConfig.staticRoutes.foreach {
      case (screenKey, route) => {
        staticRoutes(screenKey) = route.copy(
          path = prefixSlashAndRemoveTrailingSlashes(moduleName) + route.path)
      }
    }
    moduleConfig.dynamicRoutes.foreach {
      case (screenKey, route) => {
        dynamicRoutes(screenKey) = route.copy(
          path = prefixSlashAndRemoveTrailingSlashes(moduleName) + route.path)
      }
    }
    moduleConfig.staticRoutes.clear()
    moduleConfig.dynamicRoutes.clear()
  }

}
