package sri.web.router

import sri.core.{CreateElement, JSProps}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportStatic
import scala.scalajs.js.|

/**
  *  Thank you react-router and history dev :)
  */
class RouteChangePrompt
    extends RouterAwareComponentP[RouteChangePrompt.Props] {

  override def componentWillMount(): Unit = {
    if (props.blockTransition) enable(props.message)
  }

  def render() = {
    null
  }

  var unblock: js.Function0[_] = null

  def enable(message: String | js.Function2[Location, String, _]) = {
    if (unblock != null) {
      unblock()
    }
    unblock = navigation.config.history.block(props.message)
  }

  override def componentWillReceiveProps(nextJSProps: JSProps {
    type ScalaProps = RouteChangePrompt.Props
  }): Unit = {
    if (nextJSProps.scalaProps.blockTransition) {
      if (!props.blockTransition || (props.message != nextJSProps.scalaProps.message)) {
        enable(nextJSProps.scalaProps.message)
      }

    } else disable()
  }

  def disable() = {
    if (unblock != null) {
      unblock()
      unblock = null
    }
  }

  override def componentWillUnmount(): Unit = {
    disable()
  }
}

object RouteChangePrompt {

  @JSExportStatic
  val contextTypes = navigationContext

  case class Props(blockTransition: Boolean,
                   message: String | js.Function2[Location, String, _])

  def apply(blockTransition: Boolean,
            message: String | js.Function2[Location, String, _]) =
    CreateElement[RouteChangePrompt](Props(blockTransition, message))
}
