package sri.web.router

import sri.web.ReactDOM
import RouterExample._
import sri.core.REACT_ELEMENT_TYPE

import scala.scalajs.js.Dynamic.{global, literal => json}
class RouterTest extends BaseTest {

  after {
    ReactDOM.unmountComponentAtNode(app)
  }

  def render() = {
    val element = Router(RouterExample.Config)
    ReactDOM.render(element, app)
  }

  test("should render home component on initial render") {
    render()
    assert(app.textContent == "home")
  }
  //TODO add more tests
}
