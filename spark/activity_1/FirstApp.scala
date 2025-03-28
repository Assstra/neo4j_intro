import org.apache.spark.sql.SparkSession

object FirstApp {
  def main(args: Array[String]): Unit = {
    // Specify the path to your log file
    val csvFile = "data/users.csv" // Should be some file on your system

    // Add the master URL to the SparkSession builder
    val spark = SparkSession.builder
      .appName("Simple Application")
      .master("local[*]")  // This configures Spark to use all available local cores
      .getOrCreate()

    // Read the CSV into a DataFrame
    val df = spark.read
      .option("header", "true") // Use first row as header
      .option("inferSchema", "true") // Infer data types of columns
      .csv(csvFile)

    // Show the content of the DataFrame
    println("Entire DataFrame:")
    df.show()

    // Filter rows where city is 'New York' and show them
    println("People from New York:")
    df.filter(df("city") === "New York").show()

    // Stop the Spark session
    spark.stop()
  }
}