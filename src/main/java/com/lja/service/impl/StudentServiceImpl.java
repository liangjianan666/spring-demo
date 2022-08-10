package com.lja.service.impl;

import com.lja.infrastructure.converter.StudentConvert;
import com.lja.infrastructure.domain.Student;
import com.lja.infrastructure.dto.StudentDTO;
import com.lja.infrastructure.vo.StudentVO;
import com.lja.mapper.StudentMapper;
import com.lja.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author liangjianan
 * @Description student逻辑实现层
 * @Date 2021/10/12 14:13
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public static void main(String[] args) {
        StudentServiceImpl studentService = new StudentServiceImpl();
        studentService.listStudent();
    }

    @Override
    public List<StudentVO> listStudent() {
        List<Student> studentList = studentMapper.listStudent();
        return StudentConvert.INSTANCE.domain2voList(studentList);
    }

    @Override
    public void add(StudentDTO studentDTO) {
        Student student = StudentConvert.INSTANCE.dto2domain(studentDTO);
        Random random = new Random();
        student.setId(String.valueOf(random.nextInt(1000)));
        studentMapper.add(student);
    }

    @Override
    public void update(StudentDTO studentDTO) {
        Student student = StudentConvert.INSTANCE.dto2domain(studentDTO);
        studentMapper.update(student);
    }

    @Override
    public void delete(String id) {
        studentMapper.delete(id);
    }

}
