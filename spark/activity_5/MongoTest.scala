import org.apache.spark.sql.SparkSession
import com.mongodb.spark._

object MongoTest {
  def main(args: Array[String]): Unit = {

    // Create a Spark session with custom configurations
    val spark = SparkSession.builder
      .appName("Stackoverflow Application")
      .master("local[*]") // Run locally using all available CPU cores
      .config("spark.mongodb.read.connection.uri", "mongodb+srv://user2:oselTxxMFXstYFvH@cluster0.w5lqypf.mongodb.net/sample_mflix?retryWrites=true&w=majority")
      .config("spark.mongodb.write.connection.uri", "mongodb+srv://user2:oselTxxMFXstYFvH@cluster0.w5lqypf.mongodb.net/sample_mflix?retryWrites=true&w=majority")
      .getOrCreate()

    // Read the 'users' collection from MongoDB
    val usersDF = spark.read
      .format("mongodb")
      .option("collection", "users") // Specify the collection to read from
      .load()

    // Show the DataFrame content
    usersDF.show()

    // Stop the Spark session
    spark.stop()
  }
}
