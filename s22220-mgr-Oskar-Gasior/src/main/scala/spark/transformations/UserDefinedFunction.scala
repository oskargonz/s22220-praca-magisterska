package spark.transformations

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object UserDefinedFunction {

  def apply(sparkSession: SparkSession): Unit ={
    val df = sparkSession.read
      .format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("data/survey.csv")

    df.show(5)
    val predictGenderUDF = udf(predictGender(_:String): String)

    val df2 = df.withColumn("Gender", predictGenderUDF(col("Gender")))
    df2.show(5)
  }

  def predictGender(data: String): String = {
    val female = "^f$|f.m|w.m".r
    val male = "^m$|m.l|ma".r

    if (female.findFirstIn(data.toLowerCase).nonEmpty) "Female"
    else if (male.findFirstIn(data.toLowerCase).nonEmpty) "Male"
    else "N/A"
  }
}
