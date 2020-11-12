package com.library;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class LibraryApplicationTests
{

  @Autowired
  private ApplicationContext context;

  @Autowired
  private SqlSessionFactory sessionFactory;

  @Test
  void contextLoads()
  {
  }

  @Test
  public void testByApplicationContext()
  {
    try
    {
      System.out.println("=========================");
      System.out.println(this.context.getBean("sqlSessionFactory")); // DBConfiguration 클래스의 sqlSessionFactory 메서드의 이름을 getBean 메서드의 인자로 지정
      System.out.println("=========================");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testBySqlSessionFactory() {
    try {
      System.out.println("=========================");
      System.out.println(this.sessionFactory.toString()); // 위의 getBean 메서드의 인자로 지정한 sqlSessionFactory와 @Autowired로 주입한 sessionFactory는 동일한 SqlSessionFactory 객체
      System.out.println("=========================");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}