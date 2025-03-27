import org.apache.spark.sql.SparkSession

object Neo4jTest {
  def main(args: Array[String]): Unit = {

    // Init values
    val url = "neo4j://localhost:7687"
    val username = "neo4j"
    val password = "password"
    val dbname = "neo4j"

    // Create a Spark session with custom configurations
    val spark = SparkSession.builder
      .appName("Neo4J to spark Application")
      .master("local[*]") // Run locally using all available CPU cores
      .config("neo4j.url", url)
      .config("neo4j.authentication.basic.username", username)
      .config("neo4j.authentication.basic.password", password)
      .config("neo4j.database", dbname)
      .getOrCreate()

    // Read from Neo4j
    spark.read
      .format("org.neo4j.spark.DataSource")
      .option("labels", "Person")
      .load()
      .show()

    // Stop the Spark session
    spark.stop()
  }
}
