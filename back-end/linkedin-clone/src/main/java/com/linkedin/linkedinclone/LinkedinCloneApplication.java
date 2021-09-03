package com.linkedin.linkedinclone;

import com.linkedin.linkedinclone.enumerations.RoleType;
import com.linkedin.linkedinclone.model.Picture;
import com.linkedin.linkedinclone.model.Role;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.PictureRepository;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import static com.linkedin.linkedinclone.utils.PictureSave.compressBytes;

@SpringBootApplication
public class LinkedinCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkedinCloneApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(redirectConnector());
		return tomcat;
	}

	private Connector redirectConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("https");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("*");
			}
		};
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository, PictureRepository pictureRepository, BCryptPasswordEncoder encoder) {
		return args -> {

			Role admin_role = new Role(RoleType.ADMIN);
			roleRepository.save(admin_role);
			Role prof_role = new Role(RoleType.PROFESSIONAL);
			roleRepository.save(prof_role);

			User user = new User(
					"admin@mail.com",
					encoder.encode("012345"),
					"admin",
					"admin"
			);
			Set<Role> roles = new HashSet<Role>();
			roles.add(admin_role);
			roles.add(prof_role);
			user.setRoles(roles);
			userRepository.save(user);

			for (int i = 0; i < 20; i++) {
				user = new User(
						"user" + i + "@mail.com",
						encoder.encode("012345"),
						"name" + i,
						"surname" + i
				);
				roles = new HashSet<Role>();
				roles.add(prof_role);
				user.setRoles(roles);
/*				File fileItem = new File("/Users/nikol/Desktop/user.jpg");
				System.out.println(fileItem.getAbsolutePath());
				FileInputStream input = new FileInputStream(fileItem);
				MultipartFile file = new MockMultipartFile(
						"fileItem",
						fileItem.getName(),
						"image/jpg",
						IOUtils.toByteArray(input)
				);
				Picture pic = new Picture(
						file.getOriginalFilename(),
						file.getContentType(),
						compressBytes(file.getBytes())
				);
				pic.setCompressed(true);
				System.out.println(pic);
				user.setProfilePicture(pic);*/
				userRepository.save(user);

				System.out.println("User saved");
			}
		};
	}

}
