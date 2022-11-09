package zio

import org.apache.spark.sql
import zio.spark.sql.SparkSession

object YoloTest {

  implicit class YoloRunOps[E, A](io: ZIO[SparkSession, E, A]) {

    def run: A = {
      implicit val unsafe: Unsafe = Unsafe.unsafe
      Runtime.default.unsafe.run(io.provide(MavenSession.session)).getOrElse(e => {
        println(e.prettyPrint)
        ??? // fix it
      }

      )
    }

  }
}

object MavenSession {
  private lazy val _session: sql.SparkSession = org.apache.spark.sql.SparkSession.builder()
    .appName("zio-spark").master("local").getOrCreate()

  val session: ULayer[SparkSession] =ZLayer.succeed(SparkSession(_session))

}