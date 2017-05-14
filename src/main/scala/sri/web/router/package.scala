package sri.web

import sri.core.ReactElement
import sri.universal.PropTypes

import scala.reflect.ClassTag
import scala.scalajs.js
import scala.scalajs.js.ConstructorTag

package object router {

  val contextTypes =
    js.Dictionary("routerctrl" -> PropTypes.`object`.isRequired)

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
  def getRouterScreenName[C <: RouterScreenClass: ConstructorTag](
      implicit ctag: ClassTag[C]) = ctag.runtimeClass.getName
}
