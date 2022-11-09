


package dummy

import org.apache.log4j.{Level, Logger}
import zio._
import zio.spark.parameter.localAllNodes
import zio.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql
import org.junit.runner.RunWith
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class ZioSparkTestSuite extends AnyFunSuite {
  Logger.getLogger("org").setLevel(Level.OFF)

  val prg: ZIO[SparkSession, Throwable, Unit] = for {
    ds <- SparkSession.sql("select * from stroumpf")
    n <- ds.count
    _ <- Console.print(n)
  } yield {}

  test("toto") {
    import YoloTest._
    prg.run
  }

}
