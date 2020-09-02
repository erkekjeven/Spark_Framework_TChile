#!/usr/bin/env bash
# Execution yarn parameters
COLA=procesamiento
DRIVER_MEMORY=20G
EXECUTOR_MEMORY=20G
NUM_EXECUTORS=50
SPARK_UI_PORT=6044
CLASS=tchile.advanced.analytics.processors.ETL_Example
TARGET=hdfs://nn:8020/applications/etl_example/spark-project_2.11-1.0.jar

# Kerberos
KEYTAB=~/.ssh/[USER].keytab
PRINCIPAL=[USER]@TCHILE.LOCAL

MENSAJE_NIFI=$1
PROCESS_NAME=$2

dir_hdfs="$(cut -d':' -f2 <<<"$MENSAJE_NIFI")"
var_log=$dir_hdfs"/log/part*"

export SPARK_MAJOR_VERSION=2

spark-submit \
  --master yarn \
  --deploy-mode cluster \
  --name   "$PROCESS_NAME" \
  --driver-memory $DRIVER_MEMORY \
  --executor-memory $EXECUTOR_MEMORY \
  --num-executors $NUM_EXECUTORS \
  --conf "spark.ui.port=$SPARK_UI_PORT" \
  --conf "spark.yarn.keytab=$KEYTAB" \
  --conf "spark.yarn.principal=$PRINCIPAL" \
  --conf "spark.yarn.queue=$COLA" \
  --conf "spark.sql.catalogImplementation=hive" \
  --conf "spark.sql.files.ignoreCorruptFiles=true" \
  --class "$CLASS" \
  --packages com.databricks:spark-csv_2.10:1.5.0 \
  --jars "../lib/ojdbc-6.jar,../lib/config-1.4.0.jar" \
  $TARGET "$MENSAJE_NIFI" "$PROCESS_NAME"

log_salida=$(hadoop fs -cat "$var_log")
echo "$log_salida"