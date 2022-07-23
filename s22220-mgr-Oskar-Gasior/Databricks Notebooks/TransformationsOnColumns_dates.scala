// Databricks notebook source
// MAGIC %fs ls /databricks-datasets/

// COMMAND ----------

// MAGIC %fs ls databricks-datasets/airlines/

// COMMAND ----------

// MAGIC %fs head /databricks-datasets/airlines/part-00000

// COMMAND ----------

val flightsDF = spark.read
.format("csv")
.option("header", "true")
.option("inferSchema", "true")
.option("samplingRatio", "0.0005")
.load("/databricks-datasets/airlines/part-00000")

// COMMAND ----------

// Refering to columns by Column String
flightsDF.select("FlightNum", "Cancelled", "Origin", "IsDepDelayed").show(10)

// COMMAND ----------

// Refering to columns by Column Objects
import org.apache.spark.sql.functions._
airlinesDF.select(column("FlightNum"), col("Cancelled"), $"Origin", 'IsDepDelayed).show(10)

// COMMAND ----------

// Columns to compose date format
airlinesDF.select(col("Year"), col("Month"), col("DayofMonth")).show(10)

// COMMAND ----------

// Creating new column with date format
airlinesDF.select(col("Year"), col("Month"), col("DayofMonth"), expr("to_date(concat(Year, Month, DayofMonth), 'yyyyMMdd') as FlightDate")).show(10)

// COMMAND ----------

airlinesDF.selectExpr("Year", "Month", "DayofMonth", "to_date(concat(Year, Month, DayofMonth), 'yyyyMMdd') as FlightDate").show(10)
