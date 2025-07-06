# 📦 Spring Boot + RabbitMQ Microservice

This microservice demonstrates using **Spring Boot** with **RabbitMQ** for sending and receiving messages. It includes:

* A REST controller to send messages
* A producer to publish messages to a queue
* A listener (consumer) to receive and process them
* Integration with Docker and Docker Compose

---

## 📁 Project Structure

```
src/
├── main/java/com/example/req/
│   ├── controller/MessageController.java        # REST endpoints
│   ├── config/RabbitMQConfig.java               # Queue definitions
│   ├── dto/MessageRequest.java                  # Model for incoming JSON
│   ├── producer/MessageProducer.java            # Publishes to RabbitMQ
│   └── listener/MessageListener.java            # Consumes messages
```

---

## 🔧 Setup Instructions

### 1. Build the application

```bash
mvn clean package -DskipTests
```

### 2. Start all services with Docker Compose

```bash
docker-compose up --build -d
```

### 3. API Endpoints

#### ➤ Send a string message

```http
POST /api/send
Content-Type: application/json

{
  "message": "Hello from RabbitMQ"
}
```

#### ➤ Send a list of numbers

```http
POST /api/send/int
Content-Type: application/json

{
  "number": [1, 2, 3, 4, 5]
}
```

---

## 💬 How It Works

### ✅ Model (DTO)

```java
public class MessageRequest {
    private String message;
    private List<Integer> number;
    // Getters and Setters
}
```

### ✅ RabbitMQ Config

```java
@Configuration
public class RabbitMQConfig {
    public static final String DEMO_QUEUE = "demo-queue";
    public static final String TEST_QUEUE = "int-queue";

    @Bean
    public Queue demoQueue() {
        return new Queue(DEMO_QUEUE, true);
    }

    @Bean
    public Queue intQueue() {
        return new Queue(TEST_QUEUE, true);
    }
}
```

### ✅ Message Producer

```java
import com.example.req.config.RabbitMQConfig;

@Component
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEMO_QUEUE, message);
    }

    public void sendNumber(Integer number) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TEST_QUEUE, number);
    }
}
```

### ✅ REST Controller

```java
@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageProducer messageProducer;

    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody MessageRequest request){
        messageProducer.sendMessage(request.getMessage());
        return ResponseEntity.ok("✅ Sent message: " + request.getMessage());
    }

    @PostMapping("/send/int")
    public ResponseEntity<String> sendInt(@RequestBody MessageRequest request){
        for (Integer number : request.getNumber()) {
            messageProducer.sendNumber(number);
        }
        return ResponseEntity.ok("✅ Sent number message: " + request.getNumber());
    }
}
```

### ✅ Listener (Consumer)

```java
@Component
public class MessageListener {

    @RabbitListener(queues = RabbitMQConfig.DEMO_QUEUE)
    public void handleStringMessage(String message) {
        System.out.println("📥 Received message: " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.TEST_QUEUE)
    public void handleIntMessage(Integer number) {
        System.out.println("📥 Received number: " + number);
    }
}
```

---

## 🧪 Testing with CURL

```bash
curl -X POST http://localhost:9090/api/send \
  -H "Content-Type: application/json" \
  -d '{"message": "Hello"}'

curl -X POST http://localhost:9090/api/send/int \
  -H "Content-Type: application/json" \
  -d '{"number": [1,2,3,4,5]}'
```

---

## 🛠️ Troubleshooting

### 1. **404 on `/api/send/int`**

✅ Make sure the method is not commented out in the controller.
✅ Restart the app:

```bash
docker-compose down
mvn clean package -DskipTests
docker-compose up --build -d
```

### 2. **RabbitMQ bad\_header errors**

✅ Don’t try to access RabbitMQ through a browser — it's a message broker, not an HTTP server.

### 3. **Listener not firing**

✅ Check if the listener is in a `@Component`
✅ Queue name in `@RabbitListener` must match `@Bean Queue`
✅ App must be connected to RabbitMQ container

---

## 🧼 Clean Up

To stop and remove all containers:

```bash
docker-compose down
```

---

## 📌 Dependencies Used

* Spring Boot 2.7+
* Spring AMQP (RabbitMQ)
* Docker & Docker Compose
* Java 8+

---