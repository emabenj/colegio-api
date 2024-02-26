package pe.colegio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ColegioApplication{

	public static void main(String[] args) {
		SpringApplication.run(ColegioApplication.class, args);
		String url = "http://localhost:8090/api";
		System.out.println(url);
	}
}