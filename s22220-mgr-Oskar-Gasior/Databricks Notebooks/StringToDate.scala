// Databricks notebook source
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

def myToDate(df: DataFrame, dateFormat: String, columnName: String): DataFrame = {
  df.withColumn(columnName, to_date(col(columnName), dateFormat))
}

// COMMAND ----------

val mySchema = StructType(List(
  StructField("ID", StringType),
  StructField("Date", StringType)
))

// Below List of Row is not distributed.
val myRows = List(
  Row("1", "04/05/2020"),
  Row("2", "05/05/2020"),
  Row("3", "06/05/2020"),
  Row("4", "07/05/2020")
)

// Converting not distributed list of Rows to two partitions.
val myRDD = spark.sparkContext.parallelize(myRows, 2)

// Creating DataFrame from distributed RDD.
val myDF = spark.createDataFrame(myRDD, mySchema)

// COMMAND ----------

myDF.printSchema
myDF.show
val newDF = myToDate(myDF, "M/d/y", "Date")
newDF.printSchema
newDF.show
