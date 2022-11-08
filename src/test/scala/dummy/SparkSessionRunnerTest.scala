package dummy

import dummy.logic.LogicTest
import org.apache.log4j.{Level, Logger}
import org.junit.runner.RunWith
import zio._
import zio.spark.parameter.localAllNodes
import zio.spark.sql.SparkSession
import zio.test._
import zio.test.junit.ZTestJUnitRunner


@RunWith(classOf[ZTestJUnitRunner])
object ZioSparkTestSpec extends ZIOSpecDefault {
  Logger.getLogger("org").setLevel(Level.OFF)

  val session: ZLayer[Any, Nothing, SparkSession] =
    SparkSession.builder
      .master(localAllNodes)
      .appName("zio-spark")
      .asLayer
      .orDie

  type SparkTestEnvironment = TestEnvironment with SparkSession
  type SparkTestSpec        = Spec[SparkTestEnvironment, Any]

  def spec: Spec[TestEnvironment, Any] = {
    val specs: Seq[Spec[SparkTestEnvironment, Any]] =
      Seq(
        LogicTest.mySpec
      )

    suite("Spark tests")(specs: _*).provideCustomLayerShared(session)
  }
}