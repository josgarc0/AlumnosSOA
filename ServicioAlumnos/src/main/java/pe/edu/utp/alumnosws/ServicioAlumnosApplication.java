package pe.edu.utp.alumnosws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ServicioAlumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioAlumnosApplication.class, args);
	}

}
