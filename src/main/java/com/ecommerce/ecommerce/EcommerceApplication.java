package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.model.role.Role;
import com.ecommerce.ecommerce.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@RequiredArgsConstructor

public class EcommerceApplication {
	private final RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	@PostConstruct
	private void initProject()
	{
		initRoles();
	}
	private void initRoles()
	{
		roleRepository.save(new Role("ADMIN"));
		roleRepository.save(new Role("CLIENT"));
	}
}
