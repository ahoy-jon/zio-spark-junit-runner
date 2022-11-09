package dummy

import dummy.logic.LogicTest
import org.apache.log4j.{Level, Logger}
import org.junit.runner.RunWith
import zio._
import zio.spark.parameter.localAllNodes
import zio.spark.sql.SparkSession
import zio.test._
import zio.test.junit.ZTestJUnitRunner
import org.apache.spark.sql

object MavenSession {
  lazy val session: sql.SparkSession = org.apache.spark.sql.SparkSession.builder()
  .appName("zio-spark").master("local").getOrCreate()
}

@RunWith(classOf[ZTestJUnitRunner])
object ZioSparkTestSuite extends ZIOSpecDefault {
  Logger.getLogger("org").setLevel(Level.OFF)

  val session =ZLayer.succeed(SparkSession(MavenSession.session))
  type SparkTestEnvironment = TestEnvironment with SparkSession
  type SparkTestSpec        = Spec[SparkTestEnvironment, Any]

  def spec: Spec[TestEnvironment, Any] = {
    val specs: Seq[Spec[SparkTestEnvironment, Any]] =
      Seq(LogicTest.mySpec)
    suite("Spark tests")(specs: _*).provideCustomLayerShared(session)
  }
}