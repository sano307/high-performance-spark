package example.util

import org.apache.spark.sql.SparkSession

object Using {
  def apply[A, B](resource: A)(process: A => B)(implicit closer: Closer[A]): B =
    try {
      process(resource)
    } finally {
      closer.close(resource)
    }
}

case class Closer[-A](close: A => Unit)

object Closer {
  implicit val sparkSessionCloser: Closer[SparkSession] = Closer(_.stop())
}
