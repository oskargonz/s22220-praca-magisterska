package spark.datasets

import org.apache.log4j.Logger
import org.apache.spark.sql.{AnalysisException, DataFrame, Dataset, Row, SparkSession}
import spark.MainSpark4


object DatasetsMain {
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

    val inputPath = "data/blogs.json"
    val inputPath2 = "data/blogs2.json"

    import spark.implicits._
    val myDataFrame = spark.read
      .json(inputPath)

    val myDataFrame2 = spark.read
      .json(inputPath2)

    try {
      val myDataSet: Dataset[BlogSchema] = myDataFrame
        .as[BlogSchema]
      logger.info("Initial Dataset:")
      myDataSet.show()
    } catch {
      case e: AnalysisException =>  logger.info("DataFrame does not apply to given schema.")
    }

    try {
      val myDataSet: Dataset[BlogSchema] = myDataFrame2
        .as[BlogSchema]
      logger.info("Initial Dataset:")
      myDataSet.show()
    } catch {
      case e: AnalysisException =>  logger.info("DataFrame does not apply to given schema.")
    }

    val myDataSet: Dataset[BlogSchema] = myDataFrame
      .as[BlogSchema]

    logger.info("Filtered Dataset:")
    val filteredDataset = myDataSet.filter { bs => bs.Hits > 10000 }
    filteredDataset.show()

    logger.info("Double operation on int row:")
    val mappedDataset = myDataSet.map { (func: BlogSchema) => func.Id * 2 }
    mappedDataset.show()
  }
}
