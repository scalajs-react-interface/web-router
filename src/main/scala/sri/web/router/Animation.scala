package sri.web.router

import org.scalajs.dom.raw.EventTarget

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal
import scala.scalajs.js.|
import scalajsplus.macros.FunctionObjectMacro

@js.native
@JSGlobal
class Animation extends EventTarget {
  var currentTime: js.UndefOr[Double] = js.native
  var effect: AnimationEffectval = js.native
  val finished: js.Promise[Animation] = js.native
  var id: String = js.native
  val pending: Boolean = js.native
  val playState: AnimationPlayState = js.native
  var playbackRate: Double = js.native
  val ready: js.Promise[Animation] = js.native
  var startTime: js.UndefOr[Double] = js.native
  var timeline: js.UndefOr[AnimationTimeLine] = js.native
  var oncancel: js.Function1[js.Any, Any] = js.native
  var onfinish: js.Function1[js.Any, Any] = js.native
  def cancel(): Unit = js.native
  def finish(): Unit = js.native
  def pause(): Unit = js.native
  def play(): Unit = js.native
  def reverse(): Unit = js.native
}

@js.native
trait AnimationEffectval extends js.Object {}
@js.native
trait AnimationPlayState extends js.Object

object AnimationPlayState {

  @inline def IDLE = "idle".asInstanceOf[AnimationPlayState]
  @inline def RUNNING = "running".asInstanceOf[AnimationPlayState]
  @inline def PAUSED = "paused".asInstanceOf[AnimationPlayState]
  @inline def FINISHED = "finished".asInstanceOf[AnimationPlayState]
}

@js.native
trait AnimationTimeLine extends js.Object {
  val currentTime: js.UndefOr[Double] = js.native
}

@js.native
trait DocumentTimeline extends AnimationTimeLine

@js.native
trait FillMode extends js.Object

object FillMode {
  @inline def NONE = "none".asInstanceOf[FillMode]
  @inline def FORWARDS = "forwards".asInstanceOf[FillMode]
  @inline def BACKWARDS = "backwards".asInstanceOf[FillMode]
  @inline def BOTH = "both".asInstanceOf[FillMode]
  @inline def AUTO = "auto".asInstanceOf[FillMode]
}

@js.native
trait PlaybackDirection extends js.Object

object PlaybackDirection {
  @inline def NORMAL = "normal".asInstanceOf[PlaybackDirection]
  @inline def REVERSE = "reverse".asInstanceOf[PlaybackDirection]
  @inline def ALTERNATE = "alternate".asInstanceOf[PlaybackDirection]
  @inline def ALTERNATE_REVERSE =
    "alternate-reverse".asInstanceOf[PlaybackDirection]
}
@js.native
trait AnimationEffectTimingReadOnly extends js.Object {
  val delay: Double = js.native
  val endDelay: Double = js.native
  val fill: FillMode = js.native
  val iterationStart: Double = js.native
  val iterations: String | Double = js.native
  val duration: String | Double = js.native
  val direction: PlaybackDirection = js.native
  val easing: String = js.native

}

trait AnimationEffectTimingProperties extends js.Object {
  val delay: js.UndefOr[Double] = js.undefined
  val endDelay: js.UndefOr[Double] = js.undefined
  val iterationStart: js.UndefOr[Double] = js.undefined
  val iterations: js.UndefOr[Double | String] = js.undefined
  val duration: js.UndefOr[Double | String] = js.undefined
  val fill: js.UndefOr[FillMode] = js.undefined
  val direction: js.UndefOr[PlaybackDirection] = js.undefined
  val easing: js.UndefOr[String] = js.undefined

}

@js.native
trait IterationCompositeOperation extends js.Object

object IterationCompositeOperation {

  @inline def REPLACE = "replace".asInstanceOf[IterationCompositeOperation]

  @inline def ACCUMULATE =
    "accumulate".asInstanceOf[IterationCompositeOperation]

}

@js.native
trait CompositeOperation extends js.Object

object CompositeOperation {

  @inline def REPLACE = "replace".asInstanceOf[CompositeOperation]
  @inline def ADD = "add".asInstanceOf[CompositeOperation]
  @inline def ACCUMULATE = "accumulate".asInstanceOf[CompositeOperation]

}

trait KeyframeEffectOptions extends AnimationEffectTimingProperties {
  val iterationComposite: js.UndefOr[IterationCompositeOperation] = js.undefined
  val composite: js.UndefOr[CompositeOperation] = js.undefined
}

trait KeyframeAnimationOptions extends KeyframeEffectOptions {
  val id: js.UndefOr[String] = js.undefined
}

@js.native
trait Animatable extends js.Object {
  def animate(keyframes: js.Object | js.Array[js.Object],
              options: Double | KeyframeEffectOptions = ???): Animation =
    js.native
  def getAnimations(): js.Array[Animation] = js.native
}

trait AnimationConfig extends js.Object {
  val keyframes: js.Object | js.Array[js.Object]
  val keyframeEffectOptions: KeyframeEffectOptions
}

object AnimationConfig {

  def apply(keyframes: js.Object | js.Array[js.Object],
            keyframeEffectOptions: KeyframeEffectOptions): AnimationConfig = {
    import scalajsplus.DangerousUnionToJSAnyImplicit._
    val p = FunctionObjectMacro()
    p.asInstanceOf[AnimationConfig]
  }
}
