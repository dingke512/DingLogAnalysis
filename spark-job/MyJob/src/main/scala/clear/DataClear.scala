package clear

import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapred.TextInputFormat
import org.apache.spark.sql.{SaveMode, SparkSession}

object DataClear {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").appName("Dataclear").getOrCreate()

    val filepath = "E:\\毕设\\data\\SogouQ\\access_log.20060806.decode.filter"
    val line = spark.sparkContext.hadoopFile(filepath,classOf[TextInputFormat],classOf[LongWritable],classOf[Text])
      .map(pair => new String(pair._2.getBytes,0,pair._2.getLength,"GBK"))
    val data = line
      .filter{
        item =>
          item != null && item.trim.split("\\s+").length == 5
      }
      .mapPartitions(
        p => p.map{
          item =>

            val arr: Array[String] = item.trim.split("\\s+")
            arr(1).replaceAll("\\[|\\]", "")


        }
      )
    import spark.implicits._
    val word = data.toDF("word")
    word.createOrReplaceTempView("words")
    spark.sql("select count(*) from words").show()
    word.coalesce(1).write.mode(SaveMode.Overwrite)
      .option("header",true)
      .format("csv")
      .save("E:\\clear_data\\weblog20060806")

    spark.catalog.dropTempView("word")


  }

}
