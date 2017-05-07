package sri.web.router

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("jsdom-global", JSImport.Default)
object JSDOMGlobal extends js.Object {
  def apply(): js.Function0[_] = js.native
}

@js.native
@JSImport("jsdom", "JSDOM")
class JSDOM(str: String) extends js.Object {
  val window: js.Dynamic = js.native
}
