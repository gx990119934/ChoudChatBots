package cloud.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author gx
 */
@EnableScheduling
@SpringBootApplication
public class CloudBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudBotApplication.class, args);
    }

}
