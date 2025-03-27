# Introduction to spark

Let's run our first program in spark. We will be using IntelliJ IDEA for this purpose.
First, we need to create a new project in IntelliJ IDEA with the following settings:

- Language: Scala
- Package manager: sbt
- Java version: 11
- Scala version: 2.12.8

Here is the code for the program:

```scala
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
```

Here is the results of the program:

```shell
25/03/26 08:39:20 INFO FileScanRDD: Reading File path: file:///home/linux_friendly/IdeaProjects/distributed_data/data/users.csv, range: 0-109, partition values: [empty row]
25/03/26 08:39:20 INFO CodeGenerator: Code generated in 8.448672 ms
25/03/26 08:39:20 INFO Executor: Finished task 0.0 in stage 3.0 (TID 3). 1633 bytes result sent to driver
25/03/26 08:39:20 INFO TaskSetManager: Finished task 0.0 in stage 3.0 (TID 3) in 67 ms on 10.112.0.110 (executor driver) (1/1)
25/03/26 08:39:20 INFO TaskSchedulerImpl: Removed TaskSet 3.0, whose tasks have all completed, from pool 
25/03/26 08:39:20 INFO DAGScheduler: ResultStage 3 (show at FirstApp.scala:26) finished in 0.087 s
25/03/26 08:39:20 INFO DAGScheduler: Job 3 is finished. Cancelling potential speculative or zombie tasks for this job
25/03/26 08:39:20 INFO TaskSchedulerImpl: Killing all running tasks in stage 3: Stage finished
25/03/26 08:39:20 INFO DAGScheduler: Job 3 finished: show at FirstApp.scala:26, took 0.091467 s
25/03/26 08:39:20 INFO SparkContext: SparkContext is stopping with exitCode 0.
+---+----+---+--------+
| id|name|age|    city|
+---+----+---+--------+
|  1|  P1| 28|New York|
|  3|  P3| 30|New York|
+---+----+---+--------+

25/03/26 08:39:20 INFO SparkUI: Stopped Spark web UI at http://10.112.0.110:4040
```
