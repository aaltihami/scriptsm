package sa.com.elm.tamms.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sa.net.elm.tamms.TammScriptsManager;

@SpringBootApplication
public class TammsApplication {
	public static void main(String[] args) {
		SpringApplication.run(TammsApplication.class, args);
	}
	
	@Bean
	public TammScriptsManager createTammScriptManager() {
		return new TammScriptsManager();
	}
}

