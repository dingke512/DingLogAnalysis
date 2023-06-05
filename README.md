# Flume+Kafka+Spark搜索日志采集分析系统



## 项目介绍

该系统使用Flume将用户的实时搜索日志数据采集到Kafka，再使用Flume拉取Kafka将采集的源数据存储到HDFS，同时使用Spark Streaming消费Kafka进行计算分析，对于离线数据使用Spark SQL分析计算，以及使用机器学习等手段挖掘将计算结果存储MySQL，使用django进行web系统开发，使用Echarts对计算结果进行可视化展示。

```yacas
├─flume-conf        // Flume配置文件
├─simulate          // 日志模拟文件
├─ml-job	        // 机器学习代码
├─spark-job         // spark 实时计算代码和离线分析代码
├─sql			   // 数据库结构及数据sql文件
├─myapp             //springboot版本
├─webapp		    // djagno 版本
├─environment.docx　 //　运行环境配置
├─runbook.docx　　　 //　系统操作说明书
└─solution.docx　　 //　相关问题解决方案
```



## 环境需求

| 需求配置项 | 配置信息     |
| ---------- | ------------ |
| 系统       | Ubuntu 16.04 |
| Hadoop     | 2.7.3        |
| Spark      | 2.3.3        |
| Flume      | 1.8.0        |
| Kafka      | 2.4.0        |
| Zookeeper  | 3.6.1        |
| Redis      | 5.0.1        |
| MySQL      | 8.0.21       |

## 整体设计

<img src="./img/数据流向.jpg" title="" alt="" width="900">

<font color="red">注：详细流程请见系统操作说明书及[演示视频](https://www.bilibili.com/video/BV15Y411N77c/?vd_source=eeb1b759fed37a9c9c11140fbcf766eb)</font>

## 数据来源

实时数据：python模拟抽取写入

离线数据：来源于搜狗实验室SogouQ完整数据集，该数据集包含200806一个月的数据，两千多万行数据

[数据集下载](http://www.sogou.com/labs/resource/q.php)，现已经不公开链接可能无法打开，如需原始数据集可联系我

## 实时计算分析

![实时计算指标](.\img\实时计算指标.png)

### 日志模拟

模拟日志文件：`log_sogon_random.py`<font color="red">注：需要将搜狗精简版放在同一个文件夹下。</font>

### 动态采集

1、启动Hadoop   `start-all.sh`

2、启动zookeeper  进入bin目录，`./zkServer.sh start`

3、启动Kafka  `./bin/kafka-server-start.sh config/server.properties`

​	 需要自己创建weblog-spark-topic  weblog-sink-topic  两个 topic  ，分区副本在这里设置1，可以自己调整

​	`kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic weblog-spark-topic`

​	`kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic weblog-sink-topic`

​	 查看`kafka topic`：`kafka-topics.sh --zookeeper localhost:2181 --list`

4、启动Flume ：分别启动a1和a3 ，A1负责监控文件，A3负责拉取kafka上传HDFS备份

​	`flume-ng agent -n a1 --conf $FLUME_HOME/conf --conf-file $FLUME_HOME/conf/file-kafka.conf -Dflume.root.logger=INFO, console`

​	`flume-ng agent --name a3 --conf $FLUME_HOME/conf --conf-file $FLUME_HOME/conf/kafka-hdfs.conf -Dflume.root.logger=INFO, console`

5、windows下启动Redis

​	  windows：`redis-server redis.windows.conf`

### 实时计算

1、启动spark streaming ：IDEA打开`StreamJob` 需要修改kafka的地址和端口以及Redis的host port

## 离线分析

![离线分析指标](.\img\离线分析指标.png)

1、离线分析计算结果已经计算完成，不需要自己重新分析，导入`sql`打开系统即可使用

2、<font color="#ff0000">##`如需自己运行`</font>：

​		Spark SQL指标分析：`MyJob`包含了sparksql清洗导出csv

​		机器学习：

​			LDA主题聚类：`lda_job.py` 

​			TextRank关键字提取：`keywords_jieba.py` 

## Web可视化

1、导入`mysql`数据库 ，sql文件在目录中，数据库名自行创建修改

2、运行django项目 `webapp`  启动命令： ``python manager.py runserver`` 注：需要求该项目中conf.yaml文件配置自己的hadoop地址，

以及dbname数据库名称。

注：myapp -- springboot版本 ，修改yaml hadoop地址即可，一个BUG：springboot版本资源监控不可用


Email: dingke00512@163.com      You can consult me if you have any problems



