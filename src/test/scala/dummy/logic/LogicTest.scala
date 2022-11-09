package dummy.logic

import org.junit.runner.RunWith
import zio.test.{Assertion, Spec, TestEnvironment, assert}
import zio.test._
import zio.test.junit.ZTestJUnitRunner

object LogicTest  {

  def spec: Spec[TestEnvironment, Any] = {
    val specs = Seq(mySpec)
    suite("my tests")(specs: _*)
  }
  val mySpec: Spec[TestEnvironment,Any] = suite("my suite")(
    test("happy path") {
      assert(1==1 )(Assertion.isTrue)
    }
  )

}
