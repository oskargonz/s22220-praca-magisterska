package spark

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.types.DataTypes
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

import java.util.Properties
import scala.io.Source

object MainSpark3 extends Serializable {

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

    val covidDF: DataFrame = loadDF(spark, args(0))
    val partitionedCovidDF: Dataset[Row] = covidDF.repartition(4)
    val countTopCountriesDF: DataFrame = SimpleTransformations.topCountries(partitionedCovidDF)

    SimpleTransformations.basicColumnOperations(covidDF, "Direction")
    // countTopCountriesDF.show(false) // Uncomment this line to see result of topCountries method.
    logger.info(countTopCountriesDF.collect().mkString("->")) // Action

    logger.info("Finished s22220-mgr application.")
    //scala.io.StdIn.readLine() // Uncomment this line to use Spark Web UI
    spark.stop()
  }

  def loadDF(spark: SparkSession, dataFile: String): DataFrame = {
    spark.read // Action
      .option("header", "true")
      .option("inferSchema", "true") // Action
      .csv(dataFile)

  }

  def getSparkAppConf: SparkConf = {
    val sparkAppConfig: SparkConf = new SparkConf

    val props = new Properties
    props.load(Source.fromFile("spark.conf").bufferedReader())
    props.forEach((k,v) => sparkAppConfig.set(k.toString,v.toString))
    sparkAppConfig
  }
}
