package slick.examples.multidb.databaseconfig

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

import slick.basic.DatabaseConfig
import slick.examples.multidb.Util
import slick.jdbc.JdbcProfile

object SimpleExample extends App {

  // #dc
  private val dc = DatabaseConfig.forConfig[JdbcProfile]("h2_dc")

  // Import the JdbcProfile API from the configured profile
  import dc.profile.api.*
  // #dc

  // noinspection DuplicatedCode,NoTargetNameAnnotationForOperatorLikeDefinition
  private class Props(tag: Tag) extends Table[(String, String)](tag, "PROPS") {
    def key   = column[String]("KEY", O.PrimaryKey)
    def value = column[String]("VALUE")
    def *     = (key, value)
  }
  private val props = TableQuery[Props]

  private def get(key: String): DBIO[Option[String]] =
    props.filter(_.key === key).map(_.value).result.headOption

  try {
    // Initialize the Database
    val db = dc.db
    val f  = db.run(
      DBIO
        .seq(
          props.schema.create,
          props += ("foo", "bar"),
          get("foo").map(r => println("- Value for key 'foo': " + r)),
          get("baz").map(r => println("- Value for key 'baz': " + r))
        )
        .withPinnedSession
    )
    val f2 = f andThen { case _ => db.close }
    Await.result(f2, Duration.Inf)
  } finally Util.unloadDrivers()
}
