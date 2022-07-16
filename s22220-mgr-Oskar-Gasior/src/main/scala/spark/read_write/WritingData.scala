package spark.read_write

import org.apache.log4j.Logger
import org.apache.spark.sql.functions.spark_partition_id
import org.apache.spark.sql.{SaveMode, SparkSession}
import spark.MainSpark4
import spark.read_write.ReadingDataSource.getClass

object WritingData {
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

    val dataSource = spark.read.format("parquet")
      .option("path", "data/flightsLearningSpark.parquet")
      .load()

    dataSource.write
      .format("parquet")
      .mode(SaveMode.Overwrite)
      .option("path", "output/write_one_partition.parquet")
      .save()

    logger.info("Number of partitions in dataSource: " + dataSource.rdd.getNumPartitions)
    dataSource.groupBy(spark_partition_id()).count().show()

    val repartitionedDataSource = dataSource.repartition(3)
    logger.info("Number of partitions in repartitionedDataSource: " + repartitionedDataSource.rdd.getNumPartitions)
    repartitionedDataSource.groupBy(spark_partition_id()).count().show()

    repartitionedDataSource.write
      .format("parquet")
      .mode(SaveMode.Overwrite)
      .option("path", "output/write_three_partitions.parquet")
      .save()
    dataSource.show(5)

    dataSource.write
      .format("parquet")
      .mode(SaveMode.Overwrite)
      .option("path", "output/write_partition_by.parquet")
      .partitionBy("DEST_COUNTRY_NAME")
      .save()

    spark.sql("CREATE DATABASE IF NOT EXISTS BUCKET_BY")
    spark.catalog.setCurrentDatabase("BUCKET_BY")

//    dataSource.write
//      .format("parquet")
//      .mode(SaveMode.Overwrite)
//      .bucketBy(5, "DEST_COUNTRY_NAME")
//      .saveAsTable("write_bucket_by")

    spark.catalog.listTables("BUCKET_BY").show()
  }
}
