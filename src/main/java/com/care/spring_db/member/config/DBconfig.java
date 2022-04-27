package com.care.spring_db.member.config;

import java.io.IOException;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
//DB 연결 Pool을 만드는 것
//context-root에서 DB연결을 하던 것을 java class기반으로 설계하는 방법 
@Configuration
@MapperScan(basePackages = {"com.care.spring_db.member.dao","com.care.spring_db.ajax"}) //기본 문자열 배여ㅕㄹ이기 때문에 {}중괄호 필요 
public class DBconfig {
	
	//hikari 역활 : DB커넥션 풀 관리 및 컨테이너에 등록 용
	@Bean //hikari 라이브러리를 스프링 컨테이너에 등록하기 위함
	public HikariDataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");// 또는 "oracle.driver.jdbc.OracleDriver"도 가능하다.
		hikariConfig.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		hikariConfig.setUsername("spdb");
		hikariConfig.setPassword("spdb1");
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		
		return dataSource;//리턴하는 데이터소스 참조값을 @Bean을 이용하여 컨테이너에서 사용한다.	
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory() throws IOException {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());//sql에서 온 데이터를 반환하기 위한 경로 설정
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactory.setMapperLocations(resolver.getResources("/mappers/**/*Mapper.xml"));
		//setMapperLocations은 단순 String 변수는 읽지 못하기 때문에 PathMatchingResourcePatternResolver를 이용하여 변환하여 보내야 함
		//resolver가 동작시 예외 상황 발생의 대처를 위하여 throws 메소드 사용
		
		return sqlSessionFactory;
		
	}

}
