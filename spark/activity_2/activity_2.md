# Run the spark quick start program on Kubernetes using Spark operator

## How To

Create the spark operator and MinIO database:

```bash
helmfile apply
```

Create the SparkApplication & serviceAccount:

```bash
k apply -f SparkApplication.yaml
```

##  Results

Here are the results from spark application logs:

![](loading_csv_from_minio.png)