package com.tkpm.studentsmanagement.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// import org.springframework.security.core.context.SecurityContextHolder;

import com.tkpm.studentsmanagement.entity.UserEntity;

@Configuration
@EnableJpaAuditing
public class AuditorConfiguration {

    public class CustomAuditorAware implements AuditorAware<UserEntity> {

        @Override
        public Optional<UserEntity> getCurrentAuditor() {
            // String name =
            // SecurityContextHolder.getContext().getAuthentication().getName();
            // Handle sau do chua dang nhap
            UserEntity userEntity = new UserEntity();
            userEntity.setId(Long.parseLong("1"));
            return Optional.of(userEntity);
        }
    }

    @Bean
    public CustomAuditorAware auditorProvider() {
        return new CustomAuditorAware();
    }
}
