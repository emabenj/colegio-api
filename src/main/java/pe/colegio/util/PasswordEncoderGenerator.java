package pe.colegio.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGenerator {
	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("123456"));//$2a$10$ygih.V4TQ2yYA1ewvdEj2uwS3ieEFzdd.FWrqkJiikDuYLr5ggMoe
	}
}