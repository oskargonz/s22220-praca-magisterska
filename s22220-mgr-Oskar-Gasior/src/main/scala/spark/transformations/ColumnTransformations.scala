package spark.transformations

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType

object ColumnTransformations {
  def apply(sparkSession: SparkSession) = {
    val data = List(
      ("Adam", "13", "6", "83"),
      ("Adam", "13", "6", "83"),
      ("Julia", "22", "2", "4"),
      ("Karolina", "17", "2", "69"),
      ("Maciej", "26", "2", "85")
    )
    val df = sparkSession.createDataFrame(data).toDF("name", "d", "m", "y").repartition(2)

    val dfId = df.withColumn("id", monotonically_increasing_id())

    val dfYearDouble = dfId
      .withColumn("y", expr(
        """
          |case when y <21 then y + 2000
          |when y < 100 then y + 1900
          |else y
          |end
          |""".stripMargin
      ))

    val dfYearInt1 = dfId
      .withColumn("y", expr(
        """
          |case when y <21 then cast(y as int) + 2000
          |when y < 100 then cast(y as int) + 1900
          |else y
          |end
          |""".stripMargin
      ))

    dfYearInt1.show()

    val dfYearInt2 = df
      .withColumn("id", monotonically_increasing_id())
      .withColumn("d", col("d").cast(IntegerType))
      .withColumn("m", col("m").cast(IntegerType))
      .withColumn("y", col("y").cast(IntegerType))
      .withColumn("y", expr(
        """
          |case when y <21 then y + 2000
          |when y < 100 then y + 1900
          |else y
          |end
          |""".stripMargin
      ))

    val finalDF = dfYearInt2
      .withColumn("birthDate", expr("to_date(concat(d,'/',m,'/',y), 'd/M/y')"))
      .drop("d", "m", "y")
      .dropDuplicates("name", "birthDate")
      .sort(expr("birthDate desc"))

    finalDF.show()
  }
}
