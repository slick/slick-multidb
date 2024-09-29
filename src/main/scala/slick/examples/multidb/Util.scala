package slick.examples.multidb

import java.sql.DriverManager

import scala.jdk.CollectionConverters.*

object Util {

  /** A helper function to unload all JDBC drivers so we don't leak memory */
  def unloadDrivers(): Unit =
    DriverManager.getDrivers.asScala.foreach { d =>
      DriverManager.deregisterDriver(d)
    }
}
