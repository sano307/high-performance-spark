package example.c03

import example.util.Using
import org.apache.spark.sql.SparkSession

case class BigThree(
  benchPress: Int,
  squat: Int,
  deadlift: Int
)

case class Member(
  name: String,
  bigThree: BigThree
)

object Boot {
  def main(args: Array[String]) {
    var builder = SparkSession.builder().master("local[*]")

    Using(builder.getOrCreate()) { spark =>
      import spark.implicits._

      val inseo = Member("inseo", BigThree(110, 140, 150))
      val larry = Member("larry", BigThree(270, 370, 400))
      val sarah = Member("sarah", BigThree(130, 230, 250))
      val dataset = spark.createDataFrame(Seq(inseo, larry, sarah))

      dataset.printSchema()
      dataset.show()
      dataset.filter(
        $"bigThree.benchPress" > 120
        and $"bigThree.squat" > 300
      ).show()
    }
  }
}
