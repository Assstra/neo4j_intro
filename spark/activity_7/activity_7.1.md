# Implementat Apache Spark File streaming

The goal of this activity is to enable streaming from a Minio database.

## Code

```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

object SparkStreaming {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("SparkStreaming")
      .master("local[*]") // Adjust for your cluster setup
      .config("spark.hadoop.fs.s3a.endpoint", "http://localhost:9000") // MinIO URL
      .config("spark.hadoop.fs.s3a.access.key", "rootuser") // MinIO Access Key
      .config("spark.hadoop.fs.s3a.secret.key", "rootpass123") // MinIO Secret Key
      .config("spark.hadoop.fs.s3a.path.style.access", "true") // Required for MinIO
      .getOrCreate()

    // Define schema explicitly
    val schema = StructType(Seq(
      StructField("id", IntegerType, nullable = true),
      StructField("name", StringType, nullable = true),
      StructField("age", IntegerType, nullable = true)
    ))

    val usersDF = spark.readStream
      .option("header", "true")
      .schema(schema)
      .csv(s"s3a://streaming/")

    val query = usersDF.writeStream
      .outputMode("update")
      .format("console")
      .start()

    query.awaitTermination()
  }
}
```

## Results

As we can see in the screenshot below, the first file present in Minio is read and the data is displayed in the console.  
![](minio_1.png)

We can see that as there are no new files in the Minio bucket, the program is waiting for new files to be added. The program will read the new files and display the data in the console.
![](minio_2.png)

Next, I manually upload a file to the Minio bucket. The file is read and the data is displayed in the console.
![](minio_3.png)
