package com.clemhlrdt.recipeapp.security;

import com.clemhlrdt.recipeapp.exception.ResourceNotFoundException;
import com.clemhlrdt.recipeapp.model.User;
import com.clemhlrdt.recipeapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/*
	Service used to load our users from the db.
	We can load a user by usernameOrEmail or by id
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

	// Inject user repository to fetch user data
	@Autowired
	UserRepository userRepository;

	// Used by Spring Security
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

		return UserPrincipal.create(user);
	}

	// Used by JwtAuthenticationFilter
	@Transactional
	public UserDetails loadUserById(UUID id){
		User user = userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("User", "id", id)
		);

		return UserPrincipal.create(user);
	}
}
