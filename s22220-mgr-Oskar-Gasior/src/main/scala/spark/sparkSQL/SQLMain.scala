package spark.sparkSQL

import org.apache.log4j.Logger
import org.apache.parquet.format.IntType
import org.apache.spark.sql.types.{DataType, StringType, StructField}
import org.apache.spark.sql.{DataFrame, SparkSession}
import spark.MainSpark4

object SQLMain {
  @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      logger.error("Usage: input data not found.")
      System.exit(1)
    }

    logger.info("Starting s22220-mgr application.")

    val spark: SparkSession = SparkSession.builder()
      .config(MainSpark4.getSparkAppConf)
      .getOrCreate()

    val inputPath = "data/departuredelays.csv"

    val myDataFrame = spark.read.format("csv")
      .option("inferSchema", "true")
      .option("header", "true")
      .load(inputPath)


    // Create a temporary view
    myDataFrame.createOrReplaceTempView("flightsTbl")

    spark.sql("""
             SELECT *
             FROM flightsTbl
             WHERE distance < 500
             ORDER BY distance ASC""").show(10)

    spark.sql("""
             SELECT *
             FROM flightsTbl
             WHERE delay > 240 AND ORIGIN = 'ORD' AND DESTINATION = 'SFO'
             ORDER by delay DESC""").show(10)



    spark.sql("""
             SELECT *,
             CASE
             WHEN distance > 4000 THEN 'Very Long Travel'
             WHEN distance > 2000 AND distance < 4000 THEN 'Long Travel'
             WHEN distance > 1000 AND distance < 2000 THEN 'Short Travel'
             ELSE 'Very Short Travel'
             END AS Flight_Distance
             FROM flightsTbl
             ORDER BY origin, distance DESC
             """
    ).show(10)

    spark.sql("CREATE DATABASE my_database")
    spark.sql("USE my_database")

    logger.info("Finished s22220-mgr application.")
    spark.stop()
  }
}