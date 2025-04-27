package Dio.jdbc_sample;

import org.flywaydb.core.Flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcSampleApplication {


	public static void main(String[] args) {
		SpringApplication.run(JdbcSampleApplication.class, args);
		var flyway = Flyway.configure()
			.dataSource("jdbc:mysql://localhost:3306/jdbc-sample", "root", "MySQL")
			.load();
		flyway.migrate();
	}
}
