package com.tkpm.studentsmanagement.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class AuditorConfiguration {

    public class CustomAuditorAware implements AuditorAware<Long> {

        @Override
        public Optional<Long> getCurrentAuditor() {
            // String name =
            // SecurityContextHolder.getContext().getAuthentication().getName();
            // Handle sau do chua dang nhap
            return Optional.of(Long.parseLong("1"));
        }
    }

    @Bean
    public CustomAuditorAware auditorProvider() {
        return new CustomAuditorAware();
    }
}
