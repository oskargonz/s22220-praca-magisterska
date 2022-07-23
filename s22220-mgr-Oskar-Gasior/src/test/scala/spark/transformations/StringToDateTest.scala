package spark.transformations

import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import spark.transformations.StringToDate.myToDate
import java.sql.Date

case class mySchemaTest(ID: String, CrashDate: Date)

class StringToDateTest extends AnyFunSuite with BeforeAndAfterAll {
  @transient var spark: SparkSession = _
  @transient var myDF: DataFrame = _

  override def beforeAll(): Unit = {
    spark = SparkSession.builder()
      .appName("s22220-mgr")
      .master("local[3]")
      .getOrCreate()
    val mySchema = StructType(List(
      StructField("ID", StringType),
      StructField("CrashDate", StringType)
    ))
    val myRows = List(
      Row("1", "04/05/2020"),
      Row("2", "04/05/2020"),
      Row("3", "04/05/2020"),
      Row("4", "04/05/2020")
    )
    val myRDD = spark.sparkContext.parallelize(myRows, 2)
    myDF = spark.createDataFrame(myRDD, mySchema)
  }

  test("Test 1 - data types.") {
    val rowArray = myToDate(myDF, "M/d/y", "CrashDate").collect()
    rowArray.foreach(row =>
      assert(row.get(1).isInstanceOf[Date])
    )
  }

  test("Test 2 - data value") {
    val spark2 = spark
    import spark2.implicits._
    val rowArray = myToDate(myDF, "M/d/y", "CrashDate").as[mySchemaTest].collect()
    rowArray.foreach(row =>
      assert(row.CrashDate.toString == "2020-04-05")
    )
  }



  override def afterAll(): Unit = {
    spark.stop()
  }
}
