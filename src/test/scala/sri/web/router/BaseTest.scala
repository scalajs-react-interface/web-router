package sri.web.router;

import org.scalajs.dom
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite}
import sri.core.{REACT_ELEMENT_TYPE, ReactClass, ReactElement}

import scala.scalajs.js
import scala.scalajs.js.Dynamic.global

class BaseTest extends FunSuite with BeforeAndAfter with BeforeAndAfterAll {

  def getRenderedOutput(element: ReactElement) = {
    val shallowRendere = getShallowRenderer(element)
    shallowRendere.getRenderOutput()
  }

  def getShallowRenderer(element: ReactElement) = {
    val shallowRendere = ReactTestUtils.createRenderer()
    shallowRendere.render(element)
    shallowRendere
  }

  def getMountedInstance[T <: ReactClass](element: ReactElement): T = {
    getShallowRenderer(element).getMountedInstance[T]()
  }

  var app: dom.Element = null
  override protected def beforeAll(): Unit = {
    val jsdom = new JSDOM("<!doctype html><html><body></body></html>");
    global.document = jsdom;
    global.window = jsdom.window;
    global.navigator = jsdom.window.navigator;
    global.`REACT_ELEMENT_TYPE` = REACT_ELEMENT_TYPE
    val listenr: js.Function0[_] = () => {}
    global.window.addEventListener = listenr
    val raf: js.Function0[_] = () => {
      throw new Error("requestAnimationFrame is not supported in Node")
    }
    app = global.window.document
      .createElement("app")
      .asInstanceOf[dom.Element]
    global.window.document.body.appendChild(app)
  }

  override protected def afterAll(): Unit = {}
}
