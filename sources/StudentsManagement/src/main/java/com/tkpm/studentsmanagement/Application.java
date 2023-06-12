package com.tkpm.studentsmanagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.hibernate.Hibernate;
import org.hibernate.collection.spi.PersistentBag;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

// @SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
@EnableAsync
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		// modelMapper.getConfiguration().setPropertyCondition(new Condition<Object,
		// Object>() {
		// public boolean applies(MappingContext<Object, Object> context) {
		// return Hibernate.isInitialized(context.getSource()) && context.getSource() !=
		// null;
		// }
		// });
		return modelMapper;
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		return objectMapper;
	}

	@Value("${tkpm.app.secret.key}")
	private String SECRET_KEY;
	@Value("${tkpm.app.secret.salt}")
	private String SALT;

	@Bean
	public TextEncryptor textEncryptor() {
		return Encryptors.text(this.SECRET_KEY, this.SALT);
	}
}
