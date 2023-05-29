package com.tkpm.studentsmanagement.configuration;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tkpm.studentsmanagement.entity.UserEntity;

@Configuration
@EnableJpaAuditing
public class AuditorConfiguration {

    @Autowired
    private ModelMapper modelMapper;

    @Value("${tkpm.app.admin.root.id}")
    private String IdAdminRoot;

    public class CustomAuditorAware implements AuditorAware<UserEntity> {

        @Override
        public Optional<UserEntity> getCurrentAuditor() {
            UserEntity userEntity = null;
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                CustomUserAuth userDetails = modelMapper.map(auth.getPrincipal(), CustomUserAuth.class);
                userEntity = modelMapper.map(userDetails, UserEntity.class);

            } catch (Exception e) {
                userEntity = new UserEntity();
                userEntity.setId(Long.parseLong(IdAdminRoot));
            }
            return Optional.of(userEntity);
        }
    }

    @Bean
    public CustomAuditorAware auditorProvider() {
        return new CustomAuditorAware();
    }
}
