package sri.web.router

import scala.scalajs.js

@js.native
trait NavigationAction extends js.Object

object NavigationAction {

  @inline def PUSH = "PUSH".asInstanceOf[NavigationAction]

  @inline def REPLACE = "REPLACE".asInstanceOf[NavigationAction]

}
