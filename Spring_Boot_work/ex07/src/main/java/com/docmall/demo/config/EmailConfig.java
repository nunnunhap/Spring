package com.docmall.demo.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration // bean등록작업 // @Bean이 사용되려면 이 어노테이션도 같이 사용되어야 함.
// application.properties에 메일 설정이 있었다면 아래는 안 만들어도 자동 인식되나, email.properties는 임의로 만든거라 인식시켜줘야 함.
@PropertySource("classpath:/mail/email.properties")
public class EmailConfig {
	
	public EmailConfig() throws Exception {
		// 생성자가 호출되었단건 객체가 생성되었단 뜻. 이 클래스에 대한 빈을 알아서 생성해줌.
		log.info("EmailConfig.java constructor called ...");
	}
	
	// email.properties 파일의 설정정보를 참조
	
	// 사용 안함 (시작)
	@Value("${spring.mail.transport.protocol}")
	private String protocol; // smtp
	@Value("${spring.mail.debug}")
	private boolean debug;
	// 사용 안함 (끝)
	
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean auth;
	
	@Value("${spring.mail.properties.mail.starttls.enable}")
	private boolean starttls;
	
	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.port}")
	private int port;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Value("${spring.mail.default-encoding}")
	private String encoding;
	
	@Bean
	// 스프링에서도 new 생성자 써서 객체 생성할 수 있지만 스프링 시스템에서 자동으로 객체 생성하여 '주입'하고자 할 땐 @Bean을 사용함.
	// 어디에 주입할 것인지는 스프링이 알아서 해줌.
	// JavaMailSender은 스프링에서 메일발송하는 객체. Impl을 써도 되지만 다형성을 고려하여 interface로 진행함.
	// javaMailSender bean(주입 목적 : DI) 생성 및 스프링 컨테이너(bean을 관리해주는 스프링의 영역)에 등록
	public JavaMailSender javaMailSender() {
		// JavaMailSender mailSender = new JavaMailSenderImpl(); 로 받았었는데,
		// 생각해보니 상위 인터페이스로 받으면 자식 클래스의 메서드가 잡히지 않음.
		// JavaMailSenderImpl 클래스가 어떤 메일 서버를 이용하여 메일 발송할지 서버에 대한 정보를 구성하는 작업
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		// Properties 컬렉션 : 확장자가 properties인 파일과 관련. Properties는 (String, String)형태로 저장
		// 주로 애플리케이션의 환경설정과 관련된 속성을 저장하는데 사용되며 데이터를 파일로부터 읽고 쓰는 편리한 기능 제공
		// 컬렉션은 데이터가 들어올 때마다 메모리를 가변적으로 추가
		// email.properties의 설정을 Emailconfig class에 저장하고 그걸 properties라는 형태로 관리
		Properties properties = new Properties();
		// List, Set은 데이터 추가 시, add(), Map은 put()을 제공함.
		// Properties는 HashMap의 구버전인 HashTable을 상속받아 put() 사용
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.starttls.enable", starttls);
		
		/* 운영체제에 따라 아래의 코드로 대체해야 할 수 있음.
		 * properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
		 * properties.setProperty("mail.smtp.ssl.enable", "true"); // ssl 사용
		 */
		
		
		// 본래 mailSender.setHost()가 지원되어야 하는데 안 되었는데 이 이유가 Java 업캐스팅 시 자식 클래스의 메서드 지원이 안되는 것 때문이었음.
		mailSender.setHost(host);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		mailSender.setPort(port);
		mailSender.setJavaMailProperties(properties);
		mailSender.setDefaultEncoding(encoding);
		
		log.info("메일서버 : " + host);
		
		return mailSender;
	}
	
	
}
