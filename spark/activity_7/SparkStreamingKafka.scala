import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object SparkStreamingKafka {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("SparkStreamingKafka")
      .master("local[*]") // Adjust for your cluster setup
      .config("spark.hadoop.fs.s3a.endpoint", "http://localhost:9000") // MinIO URL
      .config("spark.hadoop.fs.s3a.access.key", "rootuser") // MinIO Access Key
      .config("spark.hadoop.fs.s3a.secret.key", "rootpass123") // MinIO Secret Key
      .config("spark.hadoop.fs.s3a.path.style.access", "true") // Required for MinIO
      .config("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem") // Ensure S3A is used
      .getOrCreate()

    import spark.implicits._

    val kafkaDF = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")  // Kafka broker address
      .option("subscribe", "inputStream.test.newApplication") // Kafka topic
      .option("startingOffsets", "latest") // Read from latest messages
      .load()

    // Convert Kafka message from binary to string
    val messagesDF = kafkaDF
      .selectExpr("CAST(value AS STRING)")
      .toDF("json_data")

    val query = messagesDF.writeStream
      .format("json")
      .option("path", "s3a://data/kafka/json/") // Output path in MinIO
      .option("checkpointLocation", "s3a://data/kafka/checkpoints/") // Checkpoint directory
      .outputMode("append")
      .start()

    query.awaitTermination()
  }
}