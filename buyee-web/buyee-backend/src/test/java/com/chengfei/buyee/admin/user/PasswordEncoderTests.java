package com.chengfei.buyee.admin.user;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTests {
    @Test
    public void testEncodePassword() {
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	String password = "88888888";
	String encodedPassword = encoder.encode(password);
	boolean isMatched = encoder.matches(password, encodedPassword);
	assertTrue(isMatched);
    }
}
