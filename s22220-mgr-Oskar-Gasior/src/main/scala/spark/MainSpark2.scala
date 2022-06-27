package spark

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object MainSpark2 extends Serializable {

  @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {

    logger.info("Starting s22220-mgr application.")
    val sparkAppConfig: SparkConf = new SparkConf()
    sparkAppConfig.setAppName("s22220-mgr")
    sparkAppConfig.setMaster("local[3]")
    // sparkAppConfig.set("spark.app.name", "s22220-mgr")
    // sparkAppConfig.set("spark.master", "local[3]")

    val spark: SparkSession = SparkSession.builder()
      .config(sparkAppConfig)
      .getOrCreate()

    logger.info("Finished s22220-mgr application.")
    spark.stop()
  }
}
