package example.c02

import example.util.Using
import org.apache.spark.sql._
import org.apache.spark.sql.SparkSession

case class Person(
  name: String,
  age: Int
)

object Boot {
  def main(args: Array[String]) {
    val builder = SparkSession.builder().master("local[*]")

    Using(builder.getOrCreate()) { spark =>
      import spark.implicits._

      val ds = Seq(Person("Inseo", 27)).toDS()
      ds.show()
    }  
  }
}
