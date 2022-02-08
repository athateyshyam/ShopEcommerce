package com.shopme.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		String rawPassword="1";
		String encode = passwordEncoder.encode(rawPassword);
		System.out.println(encode);
		boolean matches = passwordEncoder.matches(rawPassword, encode);
		assertThat(matches).isTrue();
	}
}
