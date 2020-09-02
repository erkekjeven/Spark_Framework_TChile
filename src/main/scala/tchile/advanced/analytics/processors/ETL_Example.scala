package tchile.advanced.analytics.processors

import java.sql.{Connection, DriverManager}

import com.typesafe.config.ConfigFactory
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession
import tchile.advanced.analytics.schema

object ETL_Example {
  def main(args: Array[String]) {
    val mensaje_nifi = args(0)
    val sparkSession = SparkSession.builder.enableHiveSupport().getOrCreate()
    val fs = FileSystem.get(sparkSession.sparkContext.hadoopConfiguration) // Utilizar comandos de sistema
    val fecha = mensaje_nifi.split(":")(0) // "2020-08-20"
    val year = fecha.split("-")(0); val month = fecha.split("-")(1); val day = fecha.split("-")(2)
    val dir_hdfs = mensaje_nifi.split(":")(2) // "Directorio a escribir"
    sparkSession.sparkContext.setLogLevel("INFO")
    val path_log = dir_hdfs.concat("/log")
    var salida = 1

    try {
      fs.delete(new Path(path_log), true)

      val basePath = "/data"
      val path_hdfs = basePath + "/year=" + year.toInt + "/month=" + month.toInt + "/day=" + day.toInt + "/hour={[0-9],1[0-9],2[0-3]}/part*.orc"
      val df = sparkSession.read.option("basePath", basePath).option("inferSchema", "false").schema(schema.TableSchemaExam.schema).orc(path_hdfs)

      //TABLA HIVE
      val query = sparkSession.sparkContext.textFile(dir_hdfs + "/example.hql").collect().mkString(" ").split(";")
      //Create external hive table
      sparkSession.sql(query(0))
      //synchronize partitions
      sparkSession.sql(query(1))

      val conf = ConfigFactory.load("application.conf")
      //ESCRIBE EN ORACLE
      val url = conf.getString("ORACLE.url")
      val user = conf.getString("ORACLE.user")
      val passw = conf.getString("ORACLE.password")
      val driver = conf.getString("ORACLE.driver")
      val tableSchema = "SCHEMA.ORACLE_TABLE"
      val prop = new java.util.Properties
      prop.setProperty("driver", driver)
      prop.setProperty("user", user)
      prop.setProperty("password", passw)
      //BORRA DIA EN EXADATA
      val delete = "DELETE FROM " + tableSchema + " WHERE FECHA=TO_DATE('" + fecha + "','yyyy-mm-dd')"
      var connection: Connection = null
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, user, passw)
      // create the statement, and run the select query
      val statement = connection.createStatement()
      statement.executeUpdate(delete)
      connection.close()
      //GRABA EN EXADATA
      df.write.mode("append").jdbc(url, tableSchema, prop)
      salida = 0
    }
    catch {
      case e: Exception =>
        salida = 1
        println("[ERROR] " + e)
        throw e;
    }
    finally {
      val RDDsalida = sparkSession.sparkContext.parallelize(List(salida))
      RDDsalida.coalesce(1).saveAsTextFile(path_log)
    }
    println("[INFO] proceso terminado")
  }
}
