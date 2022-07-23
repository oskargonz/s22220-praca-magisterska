package spark.transformations

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.expr

object Joins {
  def apply(sparkSession: SparkSession) = {
    val bookings = List(
      (1, 1, 312, "05/06/2022 13:00", 1),
      (2, 2, 312, "08/06/2022 14:00", 2),
      (3, 2, 134, "11/06/2022 16:00", 1),
      (4, 0, 134, "12/06/2022 20:00", 2),
      (5, 5, 134, "17/06/2022 09:00", 1),
      (6, 4, 542, "18/06/2022 14:00", 2),
      (7, 4, 123, "20/06/2022 17:00", 1)
    )
    val bookingsDF = sparkSession.createDataFrame(bookings)
      .toDF("bookingID", "courtNumber", "clientID", "bookingDate", "bookingTime[h]")

    val tennisCourts = List(
      (1, "clay"),
      (2, "clay"),
      (3, "hard"),
      (4, "hard"),
      (5, "grass")
    )
    val tennisCourtsDF = sparkSession.createDataFrame(tennisCourts)
      .toDF("courtNumber", "surface")

    println("Innitial DataFrames:")
    bookingsDF.show()
    tennisCourtsDF.show()

    val joinExpr = bookingsDF.col("courtNumber") === tennisCourtsDF.col("courtNumber")

    // Inner Join
    println("Inner Join: ")
    bookingsDF.join(tennisCourtsDF, joinExpr, "inner")
      .drop(bookingsDF.col("courtNumber"))
      .select("bookingID","courtNumber", "surface")
      .show()

    // Left outer join
    println("Left outer join: ")
    bookingsDF.join(tennisCourtsDF, joinExpr, "left")
      .drop(tennisCourtsDF.col("courtNumber"))
      .select("bookingID","courtNumber", "surface")
      .withColumn("surface", expr("coalesce(surface, 'unknown')"))
      .show()

  }
}
