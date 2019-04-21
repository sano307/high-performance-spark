package example.c02

import example.util.Using
import org.apache.spark.sql.{Dataset, SparkSession}

case class Text(
  word: String,
  count: Long
)

object Boot {
  def main(args: Array[String]) {
    val builder = SparkSession.builder().master("local[*]")

    Using(builder.getOrCreate()) { spark =>
      import spark.implicits._

      val textFile = spark.read.textFile("README.md")
      val wordAndCount = wordCount(spark, textFile)
      wordAndCount.show()
    }  
  }

  def wordCount(spark: SparkSession,
                ds: Dataset[String]): Dataset[Text] = {
    import spark.implicits._

    // stage1
    ds.flatMap(_.split(" "))
      .map((_, 1))
      // stage 2
      .groupByKey(_._1)
      .reduceGroups((a, b) => (a._1, a._2 + b._2))
      .map(_._2)
      // stage 3
      .sort($"_2".desc)
      .select(
        $"_1" as "word",
        $"_2" as "count"
      )
      .as[Text]
  }
}
