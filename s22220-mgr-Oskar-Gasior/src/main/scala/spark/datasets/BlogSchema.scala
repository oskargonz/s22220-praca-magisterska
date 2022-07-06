package spark.datasets

import java.sql.Date


case class BlogSchema(Id: Long, First: String, Last: String, Url: String, Published: String,
                      Hits: Long, Campaigns: Array[String])
