package com.WalkiePaw;

import com.WalkiePaw.utils.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
@PropertySource(
		value = "file:${user.home}/config/application-oauth.yml",
		factory = YamlPropertySourceFactory.class
)
public class WalkiePawApplication {

	public static void main(String[] args) {SpringApplication.run(WalkiePawApplication.class, args);}


	// Cross-Origin Resource Sharing (CORS)
		// http://http://localhost:5173/ 으로 부터 오는 모든 요청 허용하기
		@Bean
		public WebMvcConfigurer corsConfigurer() {
			return new WebMvcConfigurer() {
				@Override
				public void addCorsMappings(CorsRegistry registry) {
					registry.addMapping("/**") // 모든 경로에 대한 HTTP 요청을 처리
							.allowedMethods("*") // 모든 http 메소드를 허용
							.allowedOrigins("http://localhost:5173"); // "http://localhost:5173"에서 오는 요청만 허용
				}
			};
		}
	}
