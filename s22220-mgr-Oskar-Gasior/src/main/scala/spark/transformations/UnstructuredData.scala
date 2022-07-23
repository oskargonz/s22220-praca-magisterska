package spark.transformations

import org.apache.log4j.Logger
import org.apache.spark.sql.functions.substring_index
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import spark.MainSpark4

object UnstructuredData {
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

    val dataLogs: Dataset[String] = spark.read.textFile("data/apache_logs.txt")
    val dataLogsDF: DataFrame = dataLogs.toDF()

    val myReg = """^(\S+) (\S+) (\S+) \[([\w:/]+\s[+\-]\d{4})\] "(\S+) (\S+) (\S+)" (\d{3}) (\S+) "(\S+)" "([^"]*)"""".r

    import spark.implicits._

    val logsDataset: Dataset[LogsSchema] = dataLogsDF.map(row =>
      row.getString(0) match {
        case myReg (ip, name, userName, date, action, request, sth, number, bytes, website, usrAg) =>
          LogsSchema(ip, date, action, website)
      }
    )

    logsDataset.groupBy("action").count().show(false)

    logsDataset
      .withColumn("website", substring_index($"website", "/", 3))
      .groupBy("website").count().show(false)

    spark.stop()
}
}
