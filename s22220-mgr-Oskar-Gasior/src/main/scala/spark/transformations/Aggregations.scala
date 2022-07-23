package spark.transformations

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

object Aggregations {
  def apply(sparkSession: SparkSession) = {
    val invoiceDF = sparkSession.read
      .format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("data/invoices.csv")

    invoiceDF.select(
      count("Country").as("Count"),
      max("UnitPrice").as("Max Price"),
      min("UnitPrice").as("Min Price"),
      countDistinct("Country").as("Number of Countries")
    ).show()

    invoiceDF.groupBy("CustomerID", "StockCode")
      .agg(
        round(sum(expr("Quantity*UnitPrice")),2).as("TotalValue")
      ).show(5)

    // Windowing aggregation
    val NumInvoices = countDistinct("InvoiceNo").as("NumInvoices")
    val InvValue = round(sum(expr("Quantity*UnitPrice")),2).as("TotalValue")

    val df = invoiceDF
      .withColumn("InvoiceDate", to_date(col("InvoiceDate"), "dd-MM-yyyy H.m"))
      .withColumn("Year", year(col("InvoiceDate")))
      .groupBy("StockCode", "Year")
      .agg(NumInvoices, InvValue)
      .sort(asc("StockCode"))

    val runningTotalWindow = Window
      .partitionBy("StockCode")
      .orderBy("Year")
      .rowsBetween(Window.unboundedPreceding, Window.currentRow)

    df.withColumn("SumValue",
      sum("TotalValue").over(runningTotalWindow)
    ).show()
  }
}
