package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"adapter", "application", "config"})
@EntityScan(basePackages = {"model"})
@EnableJpaRepositories(basePackages = {"adapter.repository"})
public class MessageConsumerMain {

	public static void main(String[] args) {
		SpringApplication.run(MessageConsumerMain.class, args);
	}

}
