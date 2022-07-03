package spark

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{BooleanType, DataTypes, FloatType, IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

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
      StructField("CallNumber", IntegerType, nullable = true),
      StructField("UnitID", StringType, nullable = true),
      StructField("IncidentNumber", IntegerType, nullable = true),
      StructField("CallType", StringType, nullable = true),
      StructField("CallDate", StringType, nullable = true),
      StructField("WatchDate", StringType, nullable = true),
      StructField("CallFinalDisposition", StringType, nullable = true),
      StructField("AvailableDtTm", StringType, nullable = true),
      StructField("Address", StringType, nullable = true),
      StructField("City", StringType, nullable = true),
      StructField("Zipcode", IntegerType, nullable = true),
      StructField("Battalion", StringType, nullable = true),
      StructField("StationArea", StringType, nullable = true),
      StructField("Box", StringType, nullable = true),
      StructField("OriginalPriority", StringType, nullable = true),
      StructField("Priority", StringType, nullable = true),
      StructField("FinalPriority", IntegerType, nullable = true),
      StructField("ALSUnit", BooleanType, nullable = true),
      StructField("CallTypeGroup", StringType, nullable = true),
      StructField("NumAlarms", IntegerType, nullable = true),
      StructField("UnitType", StringType, nullable = true),
      StructField("UnitSequenceInCallDispatch", IntegerType, nullable = true),
      StructField("FirePreventionDistrict", StringType, nullable = true),
      StructField("SupervisorDistrict", StringType, nullable = true),
      StructField("Neighborhood", StringType, nullable = true),
      StructField("Location", StringType, nullable = true),
      StructField("RowID", StringType, nullable = true),
      StructField("Delay", FloatType, nullable = true)))

    val sfFireFile="data/Fire_Incidents.csv"
//    val fireDF: DataFrame = spark.read.schema(fireSchema)
//      .option("header", "true")
//      .csv(sfFireFile)

    val fireDF: DataFrame = spark.read.schema(fireSchema)
      .option("header", "true")
      .csv(args(0))

//    val outputPath = "output"
//    fireDF.write.format("parquet").save(outputPath)


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
