package spark

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.io.Source

object MainSpark3 extends Serializable {

  @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {

    logger.info("Starting s22220-mgr application.")

    val spark: SparkSession = SparkSession.builder()
      .config(getSparkAppConf)
      .getOrCreate()

    logger.info("Finished s22220-mgr application.")
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
