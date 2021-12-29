package es.toposervice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Application extends AbstractReactiveMongoConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected String getDatabaseName(){
		return "topographic";
	}
}
