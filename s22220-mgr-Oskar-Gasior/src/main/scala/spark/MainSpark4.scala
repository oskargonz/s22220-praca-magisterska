package spark

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, SparkSession, functions => F}
import spark.transformations.{Aggregations, ColumnTransformations, Joins, UserDefinedFunction}

import java.util.Properties
import scala.io.Source

object MainSpark4 extends Serializable {

  @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {

    if (args.length == 0) {
      logger.error("Usage: input data not found.")
      System.exit(1)
    }

    logger.info("Starting s22220-mgr application.")

    val spark: SparkSession = SparkSession.builder()
      .config(getSparkAppConf)
      .getOrCreate()

    val fireSchema: StructType = StructType(Array(
      StructField("IncidentNumber", IntegerType, nullable = true),
      StructField("ExposureNumber", IntegerType, nullable = true),
      StructField("ID", IntegerType, nullable = true),
      StructField("Address", StringType, nullable = true),
      StructField("IncidentDate", TimestampType, nullable = true),
      StructField("CallNumber", IntegerType, nullable = true),
      StructField("AlarmDtTm", TimestampType, nullable = true),
      StructField("ArrivalDtTm", TimestampType, nullable = true),
      StructField("CloseDtTm|", TimestampType, nullable = true),
      StructField("City", StringType, nullable = true),
      StructField("zipcode", IntegerType, nullable = true),
      StructField("Battalion", StringType, nullable = true),
      StructField("StationArea", IntegerType, nullable = true),
      StructField("Box", StringType, nullable = true),
      StructField("Suppression Units", IntegerType, nullable = true),
      StructField("Suppression Personnel", IntegerType, nullable = true)
    ))

    //1.  Reading csv to DataFrame with 3 methods:
    println(
      "\n___________________________________________________" +
        "\n1. Reading csv to DataFrame with 3 methods:" +
        "\n---------------------------------------------------")

    val inputPath = "data/Fire_Incidents.csv"
    val fireDFwithSchema: DataFrame = spark.read.schema(fireSchema)
      .option("header", "true")
      .csv(inputPath)
    val fireDFwithoutSchema: DataFrame = spark.read
      .option("header", "true")
      .csv(inputPath)
    val fireDFwithSampleRatio: DataFrame = spark.read
      .option("header", "true")
      .option("samplingRatio", 0.001)
      .csv(inputPath)

    println("Datatype with schema: " + fireDFwithSchema.schema("IncidentNumber").dataType)
    println("Datatype with samplingRatio: " + fireDFwithSampleRatio.schema("Incident Number").dataType)
    println("Datatype without schema: " + fireDFwithoutSchema.schema("Incident Number").dataType)

    println()
    println(
      "\n___________________________________________________" +
        "\n2. Simple transformation on DataFrame. Counting number of cities:" +
        "\n---------------------------------------------------")
    println("DataFrame contains below number of cities:")
    fireDFwithSchema
      .select("City")
      .where(col("City").isNotNull)
      .agg(countDistinct("City") as "DistinctCities")
      .show()

    println()
    println(
      "\n___________________________________________________" +
        "\n3. Changing the name of column and filtering:" +
        "\n---------------------------------------------------")
    fireDFwithSchema
      .withColumnRenamed("StationArea", "SmallStationsArea")
      .where(col("StationArea").isNotNull)
      .where(col("StationArea") < 50)
      .sort(col("StationArea").desc)
      .show(5)

    println()
    println(
      "\n___________________________________________________" +
        "\n4. Changing a column type:" +
        "\n---------------------------------------------------")
    val changedColumnTypeDF: DataFrame = fireDFwithoutSchema
      .withColumn("IncidentDate", to_date(col("Incident Date")))
      .drop("Incident Date")

    println("Initial datatype: " + fireDFwithoutSchema.schema("Incident Date").dataType)
    println("DataType after change: " + changedColumnTypeDF.schema("IncidentDate").dataType)

    println()
    println(
      "\n___________________________________________________" +
        "\n5. Working with Date methods:" +
        "\n---------------------------------------------------")
    println("Month column was constructed from IncidentDate column.")
    val DfWithMonths = changedColumnTypeDF
      .where(col("IncidentDate").isNotNull)
      .withColumn("Month", month(col("IncidentDate")))

    DfWithMonths
      .select("IncidentDate", "Month")
      .show(5)

    // Show the most popular cities
    println("The most popular cities in descending order:")
    fireDFwithSchema
      .select("City")
      .where(col("City").isNotNull)
      .groupBy("City")
      .count()
      .orderBy(desc("count"))
      .show()

    println()
    println(
      "\n___________________________________________________" +
        "\n6. Sum, avg and min calculations:" +
        "\n---------------------------------------------------")
    fireDFwithSchema
      .select(F.sum("StationArea"), F.avg("StationArea"),
        F.min("StationArea"), F.max("StationArea"))
      .show(5)

    // Uncomment below lines to save output to parquet files.
    // val outputPath = "output"
    // fireDFwithSchema.write.format("parquet").save(outputPath)

    println()
    println(
      "\n___________________________________________________" +
        "\n7. UserDefinedFunction:" +
        "\n---------------------------------------------------")
    val UDF: Unit = UserDefinedFunction(spark)

    println()
    println(
      "\n___________________________________________________" +
        "\n8. columnTransformations:" +
        "\n---------------------------------------------------")
    val columnTransformations: Unit = ColumnTransformations(spark)

    println()
    println(
      "\n___________________________________________________" +
        "\n9. Aggregations:" +
        "\n---------------------------------------------------")
    val aggregations: Unit = Aggregations(spark)

    println()
    println(
      "\n___________________________________________________" +
        "\n10. Joins:" +
        "\n---------------------------------------------------")
    val joins: Unit = Joins(spark)


    logger.info("Finished s22220-mgr application.")
    //scala.io.StdIn.readLine() // Uncomment this line to use Spark Web UI
    spark.stop()
  }

  def getSparkAppConf: SparkConf = {
    val sparkAppConfig: SparkConf = new SparkConf
    val props = new Properties
    props.load(Source.fromFile("spark.conf").bufferedReader())
    props.forEach((k,v) => sparkAppConfig.set(k.toString,v.toString))
    sparkAppConfig
  }
}
