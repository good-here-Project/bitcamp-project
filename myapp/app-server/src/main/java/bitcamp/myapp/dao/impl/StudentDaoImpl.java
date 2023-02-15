package bitcamp.myapp.dao.impl;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bitcamp.myapp.dao.StudentDao;
import bitcamp.myapp.vo.Student;

public class StudentDaoImpl implements StudentDao {

  SqlSessionFactory sqlSessionFactory;

  public StudentDaoImpl(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Student s) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      sqlSession.insert("StudentMapper.insert", s);
      sqlSession.commit();
    }
  }

  @Override
  public List<Student> findAll() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("StudentMapper.findAll");
    }
  }

  @Override
  public Student findByNo(int no) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectOne("StudentMapper.findByNo", no);
    }
  }

  @Override
  public List<Student> findByKeyword(String keyword) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      return sqlSession.selectList("StudentMapper.findAll");
    }
  }

  @Override
  public int update(Student s) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int n = sqlSession.update("StudentMapper.update", s);
      sqlSession.commit();
      return n;
    }
  }

  @Override
  public int delete(int no) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      int n = sqlSession.delete("StudentMapper.delete", no);
      sqlSession.commit();
      return n;
    }
  }

  public static void main(String[] args) throws Exception {
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(
        Resources.getResourceAsStream("bitcamp/myapp/config/mybatis-config.xml"));

    MemberDaoImpl memberDao = new MemberDaoImpl(sqlSessionFactory);
    StudentDaoImpl studentDao = new StudentDaoImpl(sqlSessionFactory);

    //    Student m = new Student();
    //    m.setName("x1");
    //    m.setEmail("x1@test.com");
    //    m.setPassword("1111");
    //    m.setTel("010-1111-1111");
    //    m.setPostNo("11001");
    //    m.setBasicAddress("강남대로");
    //    m.setDetailAddress("101호");
    //    m.setWorking(true);
    //    m.setGender('W');
    //    m.setLevel((byte)2);
    //
    //    memberDao.insert(m);
    //    studentDao.insert(m);


    //    List<Student> list = studentDao.findAll();
    //    for (Student m : list) {
    //      System.out.println(m);
    //    }

    //    Student m = studentDao.findByNo(29);
    //    System.out.println(m);

    //    Student m = new Student();
    //    m.setNo(29);
    //    m.setName("x1qq");
    //    m.setEmail("x1qq@test.com");
    //    m.setPassword("1111qq");
    //    m.setTel("010-1111-1111qq");
    //    m.setPostNo("11qqq");
    //    m.setBasicAddress("강남대로qq");
    //    m.setDetailAddress("101호qq");
    //    m.setWorking(false);
    //    m.setGender('M');
    //    m.setLevel((byte)1);
    //
    //    System.out.println(memberDao.update(m));
    //    System.out.println(studentDao.update(m));

    //    System.out.println(studentDao.delete(29));
    //    System.out.println(memberDao.delete(29));

  }
}























