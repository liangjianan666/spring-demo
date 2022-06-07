package com.lja.mapper;

import com.lja.infrastructure.domain.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liangjianan
 * @Description
 * @Date 2021/10/12 9:57
 */
@Repository
public interface StudentMapper {

    void add(Student student);

    List<Student> listStudent();

    void update(Student student);

    void delete(String id);
}
