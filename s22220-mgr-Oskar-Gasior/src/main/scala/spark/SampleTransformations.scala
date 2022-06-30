package spark

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.catalyst.dsl.expressions.DslSymbol

object SampleTransformations {
  def topCountries(df: DataFrame): DataFrame = {
    val filteredCovidDF = df.where("Value > 100000000").where("Measure == '$'")
    val selectCovid19 = filteredCovidDF.select("Direction", "Weekday", "Country", "Measure", "Value")
    val groupedCovid19 = selectCovid19.groupBy("Country")
    groupedCovid19.count()
  }
}
