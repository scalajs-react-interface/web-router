package sri.web.router

import sri.web.ReactDOM
import RouterExample._
import org.scalajs.dom

import scala.scalajs.js.Dynamic.{global, literal => json}
class RouterTest extends BaseTest {

  def render() = {
    ReactDOM.render(Router(RouterExample.Config),
                    dom.document.getElementById("app"))
  }

  test("should render home component on initial render", () => {
    render()
    expect(dom.document.body.textContent).toBe("home")
  })
  test(
    "should render second screen",
    () => {
      render()
      RouterExample.navigation.navigate[StaticSecondScreen]()
      expect(dom.document.body.textContent).toBe("second")
    }
  )
}
