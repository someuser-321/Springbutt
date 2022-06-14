package com.systango.springbutt;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication()
@EnableSpringDataWebSupport
public class SpringbuttApplication {

	@Value("${flyway.baseline.on.migrate}")
	private boolean flywayBaselineOnMigrate;

    public static void main(String[] args) {
        SpringApplication.run(SpringbuttApplication.class, args);
    }

    @Bean
    public FlywayMigrationInitializer flywayInitializer(Flyway flyway, @Value("${flyway.baseline.on.migrate: true}") boolean baseline) {
        flyway.baseline();
        return new FlywayMigrationInitializer(flyway);
    }

}
