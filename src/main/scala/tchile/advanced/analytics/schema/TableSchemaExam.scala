package tchile.advanced.analytics.schema

import org.apache.spark.sql.types._

object TableSchemaExam extends Serializable {
  val schema: StructType =  StructType(Array(
    StructField("FIELD1", LongType, nullable = true),
    StructField("FIELD2", ShortType, nullable = true),
    StructField("FIELD3", IntegerType, nullable = true),
    StructField("FIELD4", StringType, nullable = true)
  ))
}
