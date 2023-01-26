package com.spart.drone;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DroneApplication {

	public static void main(String[] args) {
//		Flyway flyway = Flyway
//				.configure()
//				.dataSource("jdbc:postgresql://localhost:5435/postgres", "drone", "drone")
//				.load();
//
////		 Start the migration
//		flyway.migrate();
		SpringApplication.run(DroneApplication.class, args);
	}

}
