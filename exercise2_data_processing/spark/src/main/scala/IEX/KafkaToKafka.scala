package IEX

import IEX.Schemas.IexSchema
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

object KafkaToKafka {

  val spark: SparkSession = SparkSession.builder()
    .appName("IEX Processor")
    .master("local[2]")
    .getOrCreate()

  def IexProcessor(): Unit = {

    // Reading from kafka:
    val IexDF: DataFrame = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:XXXX")
      .option("subscribe", "XXX") //put the correct values
      .load()
      //find how to read "from json" to a composite column (struct). TIP: use functions select, from_json, you have a suggested schema at Schemas

    // Print to console:
    IexDF.writeStream
      .format("console")
      .outputMode("append")
      .start()
      .awaitTermination()

   /* // Writing to Kafka:
    val iexJsonKafkaDF: DataFrame = IexDF.select(
      col("symbol").as("key"),
      to_json(struct(col("symbol"), col("companyName"), col("lastPriceTrade"),
        col("changePercent"), col("lastTradeTime"))).cast("String").as("value")
    ) //TIP: look at the schema you use

    iexJsonKafkaDF.writeStream
      .format("kafka")
      .option("bootstrap.servers", "localhost:9090")
      .option("topic", "XXXX")
      .option("checkpointLocation", "checkpoints")
      .start()
      .awaitTermination()*/
  }

  def main(args: Array[String]): Unit = {
    IexProcessor()
  }

}
