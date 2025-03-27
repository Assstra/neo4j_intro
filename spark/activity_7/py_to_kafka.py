import csv
import time
import json
from kafka import KafkaProducer

# Define the Kafka producer
producer = KafkaProducer(
    bootstrap_servers='localhost:9092',  # Replace with your Kafka broker
    value_serializer=lambda v: json.dumps(v).encode('utf-8')  # Serialize the data to JSON
)

# Define the Kafka topic
topic = 'inputStream.test.newApplication'

# Function to generate CSV data and send it to Kafka
def generate_csv_data():
    # Simulate generating CSV data
    fieldnames = ['id', 'name', 'age']
    rows = [
        {'id': 1, 'name': 'Alice', 'age': 30},
        {'id': 2, 'name': 'Bob', 'age': 25},
        {'id': 3, 'name': 'Charlie', 'age': 35},
        {'id': 4, 'name': 'Diana', 'age': 28}
    ]
    
    # Convert to CSV format and send each row to Kafka
    for row in rows:
        producer.send(topic, value=row)
        print(f"Sent: {row}")
        time.sleep(1)  # Wait for 1 second before sending the next row

# Main execution loop
if __name__ == '__main__':
    try:
        generate_csv_data()
    except KeyboardInterrupt:
        print("Producer stopped.")
    finally:
        producer.flush()
        producer.close()
