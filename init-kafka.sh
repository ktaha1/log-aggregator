#!/bin/bash
echo "Creating topic: logs"
sleep 10
kafka-topics.sh --bootstrap-server kafka:9092 \
                --create \
                --if-not-exists \
                --topic logs \
                --partitions 3 \
                --replication-factor 1

echo "Topic creation completed.\n Listing all topics:"
kafka-topics.sh --bootstrap-server kafka:9092 --list