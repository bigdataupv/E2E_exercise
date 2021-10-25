package IEX

import org.apache.spark.sql.types._

object Schemas {
  //Define the message schema, one message example here:
  //"symbol":"TWTR","companyName":"Twitter Inc","latestTime":"September 23, 2021","latestPrice":68.11,"change":2.46,"lastTradeTime":1704683533848
  val IexSchema: StructType = StructType(Array(
    StructField("symbol", StringType),
    StructField("companyName", StringType),
    StructField("latestPrice", DoubleType),
    StructField("change", DoubleType),
    StructField("lastTradeTime", LongType)
  ))
}

case class IexFull(
                  symbol: String,
                  companyName: String,
                  latestPrice: Double,
                  change: Double,
                  lastTradeTime: Long
                  )
