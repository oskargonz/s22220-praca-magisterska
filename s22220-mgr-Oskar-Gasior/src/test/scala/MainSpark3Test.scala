import org.apache.spark.sql.SparkSession
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite
import spark.MainSpark3.loadDF
import spark.SimpleTransformations

import scala.collection.mutable



class MainSpark3Test extends AnyFunSuite with BeforeAndAfterAll{

  @transient var spark: SparkSession = _

  override def beforeAll(): Unit = {
    spark = SparkSession.builder()
      .appName("s22220-mgr")
      .master("local[3]")
      .getOrCreate()
  }

  override def afterAll(): Unit = {
    spark.stop()
  }

  test("Test 1 - loadDF") {
    val covidDF = loadDF(spark, "data/covid19_data_NZ.csv")
    val countRows = covidDF.count()
    assert(countRows == 111438)
  }

  test("Test 2 - topCountries") {
    val covidDF = loadDF(spark, "data/covid19_data_NZ.csv")
    val countDF = SimpleTransformations.topCountries(covidDF)
    val countryMap = new mutable.HashMap[String, Long]
    countDF.collect().foreach(r => countryMap.put(r.getString(0), r.getLong(1)))
    assert(countryMap("East Asia (excluding China)") == 31)
    assert(countryMap("All") == 9528)
    assert(countryMap("China") == 92)
    assert(countryMap("Total (excluding China)") == 1537)
    assert(countryMap("United States") == 3)
  }

}
