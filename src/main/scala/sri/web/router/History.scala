package sri.web.router

import scalajsplus.{OptDefault, OptionalParam}
import scalajsplus.macros.FunctionObjectMacro
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}
import scala.scalajs.js.|

@js.native
trait History extends js.Object {

  def listen(listener: js.Function2[Location, String, _]): js.Function0[_] =
    js.native

  def block(
      message: String | js.Function2[Location, String, _]): js.Function0[_] =
    js.native

  def push(location: Location): Unit = js.native

  def replace(location: Location): Unit = js.native

  def goBack(): Unit = js.native

  def goForward(): Unit = js.native

  def go(n: Int): Unit = js.native

  def canGo(n: Int): Unit = js.native

  def createHref(location: js.Object): String = js.native

  def createPath(location: js.Object): String = js.native

  def createKey(): String = js.native

  val location: Location = js.native

  val action: String = js.native

  val length: Int = js.native
}

@js.native
@JSImport("history", JSImport.Namespace)
object History extends js.Object {

  type HistoryFunc = js.Function1[js.UndefOr[HistoryOptions], History]

  @JSName("createBrowserHistory") val createHistory: HistoryFunc = js.native

  val createHashHistory: HistoryFunc = js.native

  val createMemoryHistory: HistoryFunc = js.native

  def useQueries(history: HistoryFunc): HistoryFunc = js.native

  def useBasename(history: HistoryFunc): HistoryFunc = js.native

}

class Location(val pathname: String,
               val basename: js.UndefOr[String] = js.undefined,
               val hash: js.UndefOr[String] = js.undefined,
               val search: js.UndefOr[String] = js.undefined,
               val state: js.UndefOr[Any] = js.undefined,
               val action: js.UndefOr[String] = js.undefined,
               val key: String = "")
    extends js.Object {}

trait HistoryOptions extends js.Object

trait BrowserHistoryOptions extends HistoryOptions

trait HashHistoryOptions extends HistoryOptions

object BrowserHistoryOptions {

  @inline
  def apply(basename: OptionalParam[String] = OptDefault,
            forceRefresh: OptionalParam[Boolean] = OptDefault,
            keyLength: OptionalParam[Int] = OptDefault,
            getUserConfirmation: OptionalParam[(String, js.Function) => _] =
              OptDefault): BrowserHistoryOptions = {
    val p = FunctionObjectMacro()
    p.asInstanceOf[BrowserHistoryOptions]
  }
}

object HashHistoryOptions {

  @inline
  def apply(basename: OptionalParam[String] = OptDefault,
            keyLength: OptionalParam[Int] = OptDefault,
            hashType: OptionalParam[String] = OptDefault,
            getUserConfirmation: OptionalParam[(String, js.Function) => _] =
              OptDefault): HashHistoryOptions = {
    val p = FunctionObjectMacro()
    p.asInstanceOf[HashHistoryOptions]
  }
}

object HistoryFactory {

  /**
    * recommended for prod
    * @param options
    * @return
    */
  def browserHistory(
      options: js.UndefOr[BrowserHistoryOptions] = js.undefined) =
    History.createHistory(options)

  /**
    *
    * @param options
    * @return
    */
  def hashHistory(options: js.UndefOr[HashHistoryOptions] = js.undefined) =
    History.createHashHistory(options)

  def memoryHistory(options: js.UndefOr[HistoryOptions] = js.undefined) =
    History.createMemoryHistory(options)
}
