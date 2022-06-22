package offline

import org.apache.hadoop.io.{LongWritable, Text}
import org.apache.hadoop.mapred.TextInputFormat
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.io.File
import java.util.{Date, Properties}

case class WebLog(userid: String, keyword: String, rank: String, click: String, url: String,date:String)

object MyJob {
  def main(args: Array[String]): Unit = {
    val fileDir  = "E:\\毕设\\data\\SogouQ"
    val path = new File(fileDir)

    val spark = SparkSession.builder().master("local[*]").appName("MyJob1").getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

    val properties = new Properties()
    properties.setProperty("user","root")
    properties.setProperty("password","123456")
    val url ="jdbc:mysql://127.0.0.1:3306/dingke?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC"

    val fileList  = getFile(path)
    for (elem <- fileList) {
      val date = elem.toString.replace(".","-").split("-")(1)
      Analyse(spark,elem.toString,date,properties,url)
    }
    spark.stop()

  }
  def getFile(file:File): Array[File] ={
    val files = file.listFiles().filter(! _.isDirectory)
      .filter(t => t.toString.endsWith(".filter"))
      files ++ file.listFiles().filter(_.isDirectory).flatMap(getFile)
  }
  def Analyse (spark: SparkSession,filepath:String,date:String,properties: Properties,url:String): Unit = {

        val line = spark.sparkContext.hadoopFile(filepath,classOf[TextInputFormat],classOf[LongWritable],classOf[Text])
          .map(pair => new String(pair._2.getBytes,0,pair._2.getLength,"GBK"))
        val data = line.filter{
            item =>
              item != null && item.trim.split("\\s+").length == 5
          }.mapPartitions(
            p => p.map{
              item =>
                val arr: Array[String] = item.trim.split("\\s+")
                WebLog(arr(0), arr(1).replaceAll("\\[|\\]", ""), arr(2), arr(3), arr(4), date)
            })
        import spark.implicits._

        val df = data.toDF()
        df.createOrReplaceTempView("weblog")

        val anaUser = spark.sql("select count(distinct userid) as uv ,count(*) as total, round(count(userid) / count(distinct userid),5) as user_avg ,date from weblog group by date")
        anaUser.show()

        val tableName = "ana_user"
        anaUser.write.mode(SaveMode.Append).jdbc(url,tableName,properties)

        val userTopK = spark.sql("select userid,count(userid) as count,date from weblog group by userid,date order by count desc limit 20")
        userTopK.show()
        val tableName2 = "ana_user_topn"
        userTopK.write.mode(SaveMode.Append).jdbc(url,tableName2,properties)

        val ana_url = spark.sql("select count(distinct url) as page_count, round(count(url) / count(distinct url),5) as page_avg ,date from weblog group by date")
        ana_url.show()
        val tableName3 = "ana_url"
        ana_url.write.mode(SaveMode.Append).jdbc(url,tableName3,properties)

        val url_topN = spark.sql("select url,count(url) as count,date from weblog group by url,date order by count desc limit 20")
        url_topN.show()
        val tableName4 = "ana_url_topn"
        url_topN.write.mode(SaveMode.Append).jdbc(url,tableName4,properties)


        val ana_keyword = spark.sql("select count(distinct keyword) as word_count , round(count(*) / count(distinct keyword),5) as word_avg , round(avg(length (keyword)),5) as avg_length, date  from weblog group by date")
        ana_keyword.show()
        val tableName5 = "ana_keyword"
        ana_keyword.write.mode(SaveMode.Append).jdbc(url,tableName5,properties)

        val ana_keyword_topn = spark.sql("select keyword,count(keyword) as count,date from weblog group by keyword,date order by count desc limit 20")
        ana_keyword_topn.show()
        val tableName6 = "ana_keyword_topn"
        ana_keyword_topn.write.mode(SaveMode.Append).jdbc(url,tableName6,properties)


        val nice_count = spark.sql("select count(*) from weblog where rank = '1' and click = '1' ")
        nice_count.show()

        val nice_page = spark.sql("select keyword,click,rank,url  from weblog where rank = '1' and click = '1' limit 10 ")
        nice_page.show()
        val tableName7 = "test_nice"
        nice_page.write.mode(SaveMode.Append).jdbc(url,tableName7,properties)



    spark.catalog.dropTempView("weblog")

  }

}
