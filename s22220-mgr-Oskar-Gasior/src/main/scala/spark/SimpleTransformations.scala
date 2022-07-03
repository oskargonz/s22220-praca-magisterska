package spark

import org.apache.spark.sql.{Column, DataFrame, functions}
import org.apache.spark.sql.catalyst.dsl.expressions.{DslExpression, DslSymbol, StringToAttributeConversionHelper}
import org.apache.spark.sql.functions.{col, concat, expr}

object SimpleTransformations {
  def topCountries(df: DataFrame): DataFrame = {
    val filteredCovidDF = df.where("Value > 100000000").where("Measure == '$'")
    val selectCovid19 = filteredCovidDF.select("Direction", "Weekday", "Country", "Measure", "Value")
    val groupedCovid19 = selectCovid19.groupBy("Country")
    groupedCovid19.count()
  }

  def basicColumnOperations(df: DataFrame, colName: String): Unit = {
    // Show column names of DataFrame
    val columnNames: Array[String] = df.columns

    // Show column type
    val columnType: Column = df.col(colName)

    // Simple expression that double value.
    val doubleColumnExpression: Column = expr("Value * 2")
    val doubleColumnExpression2: Column = col("value") * 2

    // Creates DataFrame with one column.
    val doubleValue: DataFrame = df.select(doubleColumnExpression)

    // Add new calculated column do initial dataframe.
    val dfWithDoubleValue: DataFrame = df.withColumn("Double value", doubleColumnExpression)

    // Add new column that concatenated two other columns.
    val dfWithDirectionYearColumn: DataFrame =
      dfWithDoubleValue.withColumn("DirectionYear", concat(expr("Direction"), expr("Year")))

    // Three options to select column.
    val selectMethod1 = df.select(expr("Direction"))
    val selectMethod2 = df.select(col("Direction"))
    val selectMethod3 = df.select("Direction")

    // Sorting whole dataframe by value column.
    val sortedColumn = df.sort(col("Value").desc)


  }
}
