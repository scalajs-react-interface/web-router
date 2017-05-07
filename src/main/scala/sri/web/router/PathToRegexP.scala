package sri.web.router

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

@js.native
trait PathRegexp extends js.Object {
  val keys: js.Array[PathRegExpKey] = js.native

  def exec(in: String): js.UndefOr[js.Array[String]] = js.native
}

@js.native
trait PathRegExpKey extends js.Object {
  val name: String = js.native
  val prefix: String = js.native
  val delimiter: String = js.native
  val optional: Boolean = js.native
  val repeat: Boolean = js.native
  val partial: Boolean = js.native
  val asterisk: Boolean = js.native
  val pattern: String = js.native
}

@js.native
@JSImport("path-to-regexp", JSImport.Namespace)
object PathToRegexP extends js.Object {

  def apply(path: String,
            keys: js.Array[PathRegExpKey] = ???,
            options: js.Object = ???): PathRegexp = js.native

  def compile(path: String, options: js.Object = ???): PathFunction = js.native
}

@js.native
trait PathFunction extends js.Object {

  def apply(data: js.UndefOr[js.Object], options: js.Object = ???): String =
    js.native

}
