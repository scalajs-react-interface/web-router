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
  //
  test("static page with state") {
    render()

    RouterExample.routerCtrl
      .navigate[StaticStateScreen](state = json(x = 1)) // navigate using WebRouter ctrl
    assert(
      app.textContent == "Welcome to StaticState Screen, State you passed is : {\"x\":1}")

//    Config.history.push(new Location(
//      pathname = "/staticstate",
//      state = json(x = 2))) // navigate using history
//    assert(
//      app.textContent == "Welcome to StaticState Screen, State you passed is : {\"x\":2}")
  }
  //
  //  test("static page with query") {
  //    render()
  //    routerCtrl.navigateTo(StaticQueryPage, query = json(sort = 1)) // navigate using WebRouter ctrl
  //    assert(app.textContent == """Welcome to StaticQuery Screen, Query you passed is : {"sort":1}""")
  //
  //    Config.history.push(new Location(pathname = "/staticquery", query = json(sort = -1))) // navigate using history
  //    assert(app.textContent == """Welcome to StaticQuery Screen, Query you passed is : {"sort":-1}""")
  //  }
  //
  //  test("Dyanmic page") {
  //    render()
  //    routerCtrl.navigateToDynamic(DynamicPage, placeholder = "1") // navigate using WebRouter ctrl
  //    assert(app.textContent == """Dynamic id passed is  :: 1""")
  //
  //    Config.history.push(new Location(pathname = "/dynamic/2")) // navigate using history
  //    assert(app.textContent == """Dynamic id passed is  :: 2""")
  //
  //    Config.history.push(new Location(pathname = "/dynamic/2hfgh")) // navigate using history
  //    assert(app.textContent == """Dynamic id passed is  :: -1""")
  //
  //  }
  //
  //  test("Dyanmic page with state") {
  //    render()
  //    routerCtrl.navigateToDynamic(DynamicStatePage, placeholder = "1", state = json(x = 1)) // navigate using WebRouter ctrl
  //    assert(app.textContent == """Welcome to Dynamic State Screen, Passed Id : 1  and passed State  : {"x":1}""")
  //
  //    Config.history.push(new Location(pathname = "/dynamicstate/2",state = json(x = 2))) // navigate using history
  //    assert(app.textContent == """Welcome to Dynamic State Screen, Passed Id : 2  and passed State  : {"x":2}""")
  //
  //    Config.history.push(new Location(pathname = "/dynamicstate/2hfgh",state = json(x = 3))) // navigate using history
  //    assert(app.textContent == """Welcome to Dynamic State Screen, Passed Id : -1  and passed State  : {"x":3}""")
  //
  //  }
  //
  //  test("Dyanmic page with query") {
  //    render()
  //    routerCtrl.navigateToDynamic(DynamicQueryPage, placeholder = "1", query = json(x = 1)) // navigate using WebRouter ctrl
  //    assert(app.textContent == """Welcome to Dynamic Query Screen, Passed Id : 1  and passed Query  : {"x":1}""")
  //
  //    Config.history.push(new Location(pathname = "/dynamicquery/2",query = json(x = 2))) // navigate using history
  //    assert(app.textContent == """Welcome to Dynamic Query Screen, Passed Id : 2  and passed Query  : {"x":2}""")
  //
  //    Config.history.push(new Location(pathname = "/dynamicquery/2hfgh",query = json(x = 3))) // navigate using history
  //    assert(app.textContent == """Welcome to Dynamic Query Screen, Passed Id : -1  and passed Query  : {"x":3}""")
  //
  //  }
  //
  //  test("Not found page") {
  //    render()
  //    routerCtrl.navigateTo(StaticStatePage2) // navigate using WebRouter ctrl
  //    assert(app.textContent == "home")
  //
  //    Config.history.push(new Location(pathname = "/dyanmic/1/sdd")) // navigate using history
  //    assert(app.textContent == "home")
  //
  //  }
}
