package com.example.consumers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static com.example.consumers.Constants.*;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("#{environment.KAFKA_IP}")
    private String KAFKA_IP;

    @Value("#{environment.KAFKA_PORT}")
    private String KAFKA_PORT;

    @Value("#{environment.KAFKA_GROUP}")
    private String KAFKA_GROUP;

    /**
     * example
     * new Consumer1("127.0.0.1:9092", "group1", "earliest");
     */
    @Override
    public void run(String... args) throws RuntimeException {
        new Thread(() -> {
            try {
                info(KAFKA_IP + ":" + KAFKA_PORT + " - " + KAFKA_GROUP);
                if (KAFKA_IP == null)
                    throw new RuntimeException("KAFKA_IP is nullorEmpty");
                if (KAFKA_IP.equals("localhost"))
                    warn("WARN: using localhost as KAFKA_IP");
                Consumer1 c1 = new Consumer1(KAFKA_IP + ":" + KAFKA_PORT, KAFKA_GROUP, "earliest");
                c1.start(List.of("topic1"));
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }).start();
    }
}
