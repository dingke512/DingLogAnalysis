import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import redis.clients.jedis.Jedis


object StreamingJob {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[*]").setAppName("streaming")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
    val ssc = new StreamingContext(conf,Seconds(5))
    ssc.checkpoint("./checkpoint")
    Logger.getLogger("org.apache.spark").setLevel(Level.OFF)
    Logger.getLogger("org.apache.kafka.clients.consumer").setLevel(Level.OFF)
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "192.168.66.156:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "test",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )
    val topics = Array("weblog-spark-topic")
    val stream = KafkaUtils.createDirectStream[String, String](ssc, LocationStrategies.PreferConsistent,
        ConsumerStrategies.Subscribe[String, String](topics, kafkaParams))
    val line = stream.map(_.value().trim).filter(stream => stream != null && stream.trim.split("\\s+").length == 6)
      .mapPartitions(
        p => p.map{
          item => val arr:Array[String] = item.trim.split("\\s+")
            (arr(0),arr(1),arr(2),arr(3),arr(4),arr(5))
        })
    val user = line.map(log => (log._2,1))
    val word = line.map(log => (log._3,1))
    val page = line.map(log => (log._6,1))
    //网站流量
    val count = line.count()
    count.foreachRDD(
      rdd=>{
        rdd.foreachPartition(
          iter=> {
            var jedis=new Jedis("localhost",6379);
            iter.foreach(
              count=> {
                jedis.set("flow_count",count.toString)
              })
            jedis.close()
          })
      })


    //累计user

    val user_total = user.updateStateByKey(updateFunc).transform(_.distinct()).count()
    user_total.foreachRDD(
      rdd=>{
        rdd.foreachPartition(
          iter=> {
            var jedis=new Jedis("localhost",6379);
            iter.foreach(
              count=> {
                jedis.set("user_total",count.toString)
              })
            jedis.close()
          })
      })


    val page_total = page.updateStateByKey(updateFunc).transform(_.distinct()).count()
    user_total.foreachRDD(
      rdd=>{
        rdd.foreachPartition(
          iter=> {
            var jedis=new Jedis("localhost",6379);
            iter.foreach(
              count=> {
                jedis.set("page_total",count.toString)
              })
            jedis.close()
          })
      })


    //当前 uv
    val user_count = user.reduceByKey(_+_).count()
    user_count.foreachRDD(
      rdd=>{
        rdd.foreachPartition(
            iter=> {
            var jedis=new Jedis("localhost",6379);
            iter.foreach(
                count=> {
                  jedis.set("user_count",count.toString)
                })
            jedis.close()
      })
    })

    //当前页面访问数
    val page_count = page.reduceByKey(_+_).count()
    page_count.foreachRDD(
      rdd=>{
        rdd.foreachPartition(
          iter=> {
            var jedis=new Jedis("localhost",6379);
            iter.foreach(
              count=> {
                jedis.set("page_count",count.toString)
              })
            jedis.close()
          })
      })
    //当前搜索词数
    val word_count = word.reduceByKey(_+_).count()
    page_count.foreachRDD(
      rdd=>{
        rdd.foreachPartition(
          iter=> {
            var jedis=new Jedis("localhost",6379);
            iter.foreach(
              count=> {
                jedis.set("word_count",count.toString)
              })
            jedis.close()
          })
      })


    //user top10
    val user_top = user.reduceByKey(_+_)
      .transform(   //在进行DStream和RDD的混合操作的时候,需要对把函数执行于DStream中的每个RDD,返回一个新的DStream
        rdd => {
          val sortedRDD = rdd.sortBy(_._2, false) // DStream 使用RDD降序操作
          val sortRdd = sortedRDD.take(10).filter(_!=None).toMap // Map(aaa -> 3, bbb -> 2, ccc -> 1)
          val str = scala.util.parsing.json.JSONObject(sortRdd).toString() // json {"aaa": 3, "bbb": 2, "ccc": 1}
          var jedis=new Jedis("localhost",6379);
          //sink
          try { jedis.set("user_top",str)
          }
          finally
          { jedis.close()
          }
          sortedRDD           //transform算子需要返回一个新的DStream
        })

    //page top10
    val page_top = page.reduceByKey(_+_)
      .transform(   //在进行DStream和RDD的混合操作的时候,需要对把函数执行于DStream中的每个RDD,返回一个新的DStream
        rdd => {
          val sortedRDD = rdd.sortBy(_._2, false) // DStream 使用RDD降序操作
          val sortRdd = sortedRDD.take(10).filter(_!=None).toMap // Map(aaa -> 3, bbb -> 2, ccc -> 1)
          val str = scala.util.parsing.json.JSONObject(sortRdd).toString() // json {"aaa": 3, "bbb": 2, "ccc": 1}
          var jedis=new Jedis("localhost",6379);
          //sink
          try {
            jedis.set("page_top",str)
          }
          finally
          {
            jedis.close()
          }
          sortedRDD           //transform算子需要返回一个新的DStream
        }
      )

    //words top10
    val words_top = word.reduceByKey(_+_)
      .transform(   //在进行DStream和RDD的混合操作的时候,需要对把函数执行于DStream中的每个RDD,返回一个新的DStream
        rdd => {
          val sortedRDD = rdd.sortBy(_._2, false) // DStream 使用RDD降序操作
          val sortRdd = sortedRDD.take(10).filter(_!=None).toMap // Map(aaa -> 3, bbb -> 2, ccc -> 1)
          val str = scala.util.parsing.json.JSONObject(sortRdd).toString() // json {"aaa": 3, "bbb": 2, "ccc": 1}
          var jedis=new Jedis("localhost",6379);
          //sink
          try {
            jedis.set("word_top",str)
          }
          finally
          {
            jedis.close()
          }
          sortedRDD           //transform算子需要返回一个新的DStream
        }
      )


    count.print()


    user_total.print()
    page_total.print()

    user_count.print()
    page_count.print()
    word_count.print()

    words_top.print()
    user_top.print()
    page_top.print()


    ssc.start()
    ssc.awaitTermination()

  }

  val updateFunc = (seq:Seq[Int],option : Option[Int]) => {
    // 初始化一个变量
    var value = 0;
    // 该变量用于更新，加上上一个状态的值，这里隐含一个判断，如果有上一个状态就获取，如果没有就赋值为0
    value += option.getOrElse(0)
    // 遍历当前的序列，序列里面每一个元素都是当前批次的数据计算结果，累加上一次的计算结果

    for(elem <- seq){
      value +=elem
    }
    // 返回一个Option对象
    Option(value)
  }

}
