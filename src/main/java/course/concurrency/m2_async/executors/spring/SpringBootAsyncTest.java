package course.concurrency.m2_async.executors.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootAsyncTest {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAsyncTest.class, args);
    }
}
