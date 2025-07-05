# Kafka Log Aggregator

A distributed logging system built with Apache Kafka for real-time log processing and aggregation. This project demonstrates a microservices architecture with message-driven log publishing and consumption.

## Architecture

The system consists of three main modules:

```
log-aggregator/
├── log-common/          # Shared interfaces and models
├── log-publisher/       # Log generation and publishing service
├── log-consumer/        # Log consumption and processing service
└── docker-compose.yml   # Infrastructure setup
```

### Components

- **log-common**: Contains shared interfaces (`LogPublisher`, `LogConsumer`, `LogGenerator`) and the `LogEvent` model
- **log-publisher**: Generates fake log events using Quartz scheduler and publishes them to Kafka
- **log-consumer**: Consumes and processes log events from Kafka topics
- **Kafka**: Message broker for reliable log event streaming
- **Kafka UI**: Web interface for monitoring topics and messages

## Tech Stack

- **Java 17** - Programming language
- **Apache Kafka 3.9.0** - Message streaming platform
- **Quartz Scheduler 2.5.0** - Job scheduling for log generation
- **JavaFaker 1.0.2** - Fake data generation
- **SLF4J + Logback** - Logging framework
- **Maven** - Build and dependency management
- **Docker & Docker Compose** - Containerization and orchestration

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose

## Quick Start

### 1. Build the Project

```bash
mvn clean compile
```

### 2. Start Infrastructure

Make the init script executable:
```bash
chmod +x init-kafka.sh
```

Start all services:
```bash
docker-compose up -d
```

This will start:
- Kafka broker on port 9092
- Kafka UI on port 8080 (admin:admin)
- Log publisher service (containerized)
- Log consumer service (containerized)

### 3. Access Kafka UI

Open your browser and navigate to:
```
http://localhost:8080
```

Login with:
- Username: `admin`
- Password: `admin`

## Configuration

### Kafka Configuration

The system uses the following Kafka configuration:

```yaml
# log-publisher/src/main/resources/properties.yaml
kafka:
  bootstrapServers: kafka:9092
  topic: logs
```

### Log Generation

The log publisher is configured to:
- Generate 5 fake log events every 30 seconds
- Use Quartz cron expression: `0/30 * * * * ?`
- Publish to the `logs` topic with 3 partitions

### Docker Services

- **Kafka**: Bitnami Kafka image with KRaft mode (no Zookeeper required)
- **Kafka Init**: Automatically creates the `logs` topic on startup
- **Log Publisher**: Custom containerized service for log generation
- **Log Consumer**: Custom containerized service for log processing
- **Kafka UI**: Web interface for monitoring

## Development

### Project Structure

```
log-aggregator/
├── log-common/
│   └── src/main/java/com/tahakamil/kafka/logaggregator/common/
│       ├── interfaces/          # Publisher/Consumer interfaces
│       ├── model/              # LogEvent model
│       └── util/               # Utility classes
├── log-publisher/
│   └── src/main/java/com/tahakamil/kafka/logaggregator/publisher/
│       ├── app/                # Main application
│       ├── config/             # Kafka configuration
│       ├── generator/          # Log generation logic
│       ├── impl/               # Kafka publisher implementation
│       ├── job/                # Quartz job definitions
│       └── service/            # Business logic
└── log-consumer/
    └── src/main/java/com/tahakamil/logaggregator/consumer/
        ├── app/                # Main application
        ├── config/             # Kafka consumer configuration
        ├── impl/               # Kafka consumer implementation
        └── service/            # Log processing service
```

### Key Classes

**Publisher:**
- `LogEvent`: Data model for log entries with timestamp, service, level, and message
- `QuartzLogProducerMain`: Main application class with scheduled job execution
- `FakeLogGenerator`: Generates realistic fake log data using JavaFaker
- `KafkaLogPublisher`: Implements the Kafka producer for publishing log events

**Consumer:**
- `LogConsumerMain`: Main consumer application with graceful shutdown
- `KafkaLogConsumer`: Implements the Kafka consumer with subscription management
- `LogProcessor`: Processes consumed logs with error detection and timestamping
- `KafkaConsumerConfig`: Kafka consumer configuration with retry settings

### Building Individual Modules

```bash
# Build common module
mvn clean compile -pl log-common

# Build publisher module
mvn clean compile -pl log-publisher

# Build consumer module
mvn clean compile -pl log-consumer
```

### Running Locally

To run the log publisher locally (without Docker):

```bash
cd log-publisher
mvn clean package
java -jar target/log-publisher-1.0-SNAPSHOT.jar
```

To run the log consumer locally (without Docker):

```bash
cd log-consumer
mvn clean package
java -jar target/log-consumer-1.0-SNAPSHOT.jar
```

## Monitoring

### Kafka UI Features

- View topics and partitions
- Monitor message flow
- Inspect message content
- Consumer group monitoring
- Cluster health metrics

### Log Files

Application logs are stored in:
- `logs/application.log` - Publisher log file
- `logs/application.YYYY-MM-DD.log` - Publisher daily rotated logs
- `logs/consumer.log` - Consumer log file
- `logs/consumer.YYYY-MM-DD.log` - Consumer daily rotated logs

### Consumer Features

The log consumer provides:
- **Real-time processing** of all log messages from the topic
- **Error detection** with special highlighting for ERROR logs
- **Processing timestamps** added to each consumed message
- **Consumer group management** (`log-consumer-group`)
- **Graceful shutdown** with proper cleanup
- **Automatic offset management** starting from earliest available messages

## Scaling

The system is designed for horizontal scaling:

- **Kafka**: Increase partitions for higher throughput
- **Publishers**: Scale replicas for more log generation
- **Consumers**: Add consumer instances for parallel processing

## Troubleshooting

### Common Issues

1. **Kafka connectivity issues**
   - Ensure Docker containers are running: `docker-compose ps`
   - Check Kafka health: `docker-compose exec kafka kafka-topics.sh --bootstrap-server localhost:9092 --list`

2. **Topic not created**
   - Verify init script permissions: `ls -la init-kafka.sh`
   - Check init container logs: `docker-compose logs kafka-init`

3. **Publisher not generating logs**
   - Check publisher container logs: `docker-compose logs log-publisher`
   - Verify Kafka topic exists in UI

4. **Consumer not processing logs**
   - Check consumer container logs: `docker-compose logs log-consumer`
   - Verify consumer group membership in Kafka UI
   - Check if consumer is part of `log-consumer-group`

### Health Checks

All services include health checks:
- Kafka: Topic listing command
- Log Publisher: Java process check
- Log Consumer: Java process check

## Architecture Overview

The complete system now includes:

### Producer Flow
1. **Log Publisher** generates fake log events every 30 seconds
2. **Quartz Scheduler** triggers log generation jobs
3. **Kafka Producer** publishes logs to the `logs` topic

### Consumer Flow
1. **Log Consumer** subscribes to the `logs` topic
2. **LogProcessor** processes each consumed message
3. **Error Detection** identifies and highlights ERROR logs
4. **Timestamping** adds processing timestamps to all logs

### Current Implementation Status

- ✅ **log-common**: Shared interfaces and models
- ✅ **log-publisher**: Complete with Docker support and scheduled publishing
- ✅ **log-consumer**: Complete with processing pipeline and error detection
- ✅ **Docker Compose**: Full stack deployment with Kafka UI

## Future Enhancements

- [ ] Add metrics and monitoring (ElasticSearch/Kibana)
- [ ] Implement log filtering and routing
- [ ] Schema registry integration
- [ ] Multi-environment configuration
- [ ] Consumer scaling and load balancing

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is for educational and demonstration purposes.