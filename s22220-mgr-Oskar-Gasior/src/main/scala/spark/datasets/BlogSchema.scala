package spark.datasets


case class BlogSchema(Id: Long, First: String, Last: String, Url: String, Published: String,
                      Hits: Long, Campaigns: Array[String])
