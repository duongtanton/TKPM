package com.tkpm.studentsmanagement;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

// @SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	// @Bean
	// public ObjectMapper onObjectMapper(){
	// 	ObjectMapper objectMapper = new ObjectMapper();
	// 	// objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
	// 	return objectMapper;
	// }

	@Value("${tkpm.app.secret.key}")
	private String SECRET_KEY;
	@Value("${tkpm.app.secret.salt}")
	private String SALT;

	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.text(this.SECRET_KEY, this.SALT);
	}
}
