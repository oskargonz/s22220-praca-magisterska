package spark

import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession

object MainSpark1 extends Serializable {

  @transient lazy val logger: Logger = Logger.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {

    logger.info("Starting s22220-mgr application.")
    val spark: SparkSession = SparkSession.builder()
      .appName("s22220-mgr")
      .master("local[3]")
      .getOrCreate()

    logger.info("Finished s22220-mgr application.")
    spark.stop()
  }
}
