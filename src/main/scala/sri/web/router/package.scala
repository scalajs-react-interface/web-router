package sri.web

import sri.universal.PropTypes

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.ConstructorTag

package object router {

  private[sri] val navigationContext =
    js.Dictionary("navigation" -> PropTypes.`object`.isRequired)

  @inline def FORWARD_SLASH = "/"

  implicit class String_Ext_Methods(val value: String) extends AnyVal {

    @inline
    def removeForwardSlashes =
      if (value != null) value.replaceAll("/", "") else value

    @inline
    def removeTrailingSlash =
      if (value != null) value.replaceAll("/$", "") else value
  }

  @inline
  def getRouterScreenKey[C <: RouterScreenClass: ConstructorTag](
      implicit ctag: ClassTag[C]): RouterScreenKey =
    ctag.runtimeClass.getName.asInstanceOf[RouterScreenKey]
}
