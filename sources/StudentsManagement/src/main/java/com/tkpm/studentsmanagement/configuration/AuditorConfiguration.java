package com.tkpm.studentsmanagement.configuration;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.tkpm.studentsmanagement.entity.UserEntity;
import com.tkpm.studentsmanagement.repository.UserRepository;

@Configuration
@EnableJpaAuditing
public class AuditorConfiguration {

    @Autowired
    private ModelMapper modelMapper;


    public class CustomAuditorAware implements AuditorAware<UserEntity> {

        @Override
        public Optional<UserEntity> getCurrentAuditor() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserAuth userDetails = modelMapper.map(auth.getPrincipal(), CustomUserAuth.class);
            UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
            return Optional.of(userEntity);
        }
    }

    @Bean
    public CustomAuditorAware auditorProvider() {
        return new CustomAuditorAware();
    }
}
