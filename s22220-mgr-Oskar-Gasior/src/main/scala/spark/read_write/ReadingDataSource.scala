package spark.read_write

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession
import spark.MainSpark4

object ReadingDataSource {
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

    val csvPath = "data/flight*.csv"
    val jsonPath = "data/flight*.json"
    val parquetPath = "data/flight*.parquet"

    val dfCsv = spark.read.format("csv")
      .option("header", "true")
      .load(csvPath)

    val dfCsvInferSchema = spark.read.format("csv")
      .option("inferSchema", "true")
      .option("header", "true")
      .load(csvPath)

    val dfJson = spark.read.format("json")
      .load(jsonPath)

    val dfParquet = spark.read.format("parquet")
      .option("path", "data/flight-time.parquet")
      .load()

    println("CSV DataFrame: ")
    dfCsv.show(3)
    println("Json DataFrame: ")
    dfJson.show(3)
    println("Parquet DataFrame: ")
    dfParquet.show(3)

    logger.info("CSV schema: " + dfCsv.schema.simpleString)
    logger.info("CSV infer schema: " + dfCsvInferSchema.schema.simpleString)
    logger.info("Json schema: " + dfCsv.schema.simpleString)
    logger.info("Parquet schema: " + dfCsv.schema.simpleString)

    val dfParquet2 = spark.read.format("parquet")
      .option("path", "data/flightsLearningSpark.parquet")
      .load()

    dfParquet2.show(3)
    logger.info("Parquet2 schema: " + dfParquet2.schema.simpleString)

    logger.info("Finished s22220-mgr application.")
    spark.stop()
  }
}
