package com.library.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration // 자바 기반의 파일임을 명시
@PropertySource({"classpath:/application.properties"}) // 클래스에서 참조할 properties 파일의 위치 지정
public class DBConfiguration
{
  @Bean
  public LayoutDialect layoutDialect() {
      return new LayoutDialect();
  }
  
  @Autowired // Bean으로 등록된 인스턴스를 클래스에 주입하는데 사용
  /** 스프링 컨테이너로, Bean의 생성과 사용, 관계, 생명 주기 등을 관리 */
  private ApplicationContext applicationContext;

  @Bean // 클래스의 메서드 레벨에만 지정 가능, @Bean이 지정된 객체는 컨테이너에 의해 관리되는 Bean으로 등록
  @ConfigurationProperties(prefix="spring.datasource.hikari") // @PropertySource에 지정된 파일에서 prefix에 해당하는 설정을 모두 읽어 들여 메서드에 매핑
  /** 히카리CP 객체 생성 */
  public HikariConfig hikariConfig()
  {
    return new HikariConfig();
  }

  @Bean
  /** 데이터소스 객체 생성. 
   *  리소스를 줄이기 위해 커넥션 객체를 생성해두고 DB에 접근하는 사용자에게 미리 생성해둔 커넥션을 제공했다가 다시 돌려받기 위한 기능인 커넥션 풀을 지원하기 위한 인터페이스 */
  public DataSource dataSource() {
    return new HikariDataSource(hikariConfig());
  }

  @Bean
  /** SqlSessionFactory 객체 생성. DB 커넥션과 SQL 실행에 대한 모든 것을 다룸.
   *  마이바티스와 스프링의 연동 모듈로 사용되며 마이마티스 XML Mapper, 설정 파일 위치 등을 지정하고, SqlSessionFactoryBean 자체가 아닌 getObject 메서드가 리턴하는 SqlSessionFactory를 생성 */
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource());
    factoryBean.setMapperLocations(this.applicationContext.getResources("classpath:/mappers/**/*Mapper.xml")); // getResources 메서드의 인자로 지정된 패턴에 포함하는 XML Mapper를 인식하도록 하는 역할
    factoryBean.setTypeAliasesPackage("com.library.*"); // 풀 패키지 경로를 생략하기 위한 메서드
    factoryBean.setConfiguration(mybatisConfg()); // 아래 선언된 마이마티스 설정과 관련된 Bean을 설정 파일로 지정
    return factoryBean.getObject();
  }

  @Bean
  /** SqlSessionFactory는 SqlSectionFactory를 통해 생성되고, DB의 커밋, 롤백 등 SQL의 실행에 필요한 모든 매서드를 갖는 객체 */
  public SqlSessionTemplate sqlSession() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory());
  }
  
  @Bean
  @ConfigurationProperties(prefix="mybatis.configuration")
  /** application.properties에서 mybatis.configuration으로 시작하는 모든 설정을 읽어들여 Bean으로 등록 */
  public org.apache.ibatis.session.Configuration mybatisConfg() {
    return new org.apache.ibatis.session.Configuration();
  }
}