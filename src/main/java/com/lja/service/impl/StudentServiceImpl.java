package com.lja.service.impl;

import com.lja.infrastructure.converter.StudentConvert;
import com.lja.infrastructure.domain.Student;
import com.lja.infrastructure.dto.StudentDTO;
import com.lja.infrastructure.vo.StudentVO;
import com.lja.mapper.StudentMapper;
import com.lja.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author liangjianan
 * @Description student逻辑实现层
 * @Date 2021/10/12 14:13
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final static String STUDENT_CODE = "student_code";

    public static void main(String[] args) {
        StudentServiceImpl studentService = new StudentServiceImpl();
        //空指针异常：spring注入原理
        studentService.listStudent();
    }

    @Override
    public List<StudentVO> listStudent() {
        List<Student> studentList = studentMapper.listStudent();
        return StudentConvert.INSTANCE.domain2voList(studentList);
    }

    @Override
    public void add(StudentDTO studentDTO) {
        Set<String> studentCodeSet = redisTemplate.opsForSet().members(STUDENT_CODE);
        boolean flag = false;
        if (CollectionUtils.isEmpty(studentCodeSet)) {
            //去数据库查询
            //使用redis校验code不能重复
            studentCodeSet = studentMapper.getCodeList();
            flag = true;
        }
        if (!studentCodeSet.add(studentDTO.getCode())) {
            //标识，判断是否需要存到redis
            if (flag) {
                for (String studentCode : studentCodeSet) {
                    redisTemplate.opsForSet().add(STUDENT_CODE, studentCode);
                }
            }
            throw new RuntimeException("已存在学生代码：" + studentDTO.getCode());
        }

        for (String studentCode : studentCodeSet) {
            redisTemplate.opsForSet().add(STUDENT_CODE, studentCode);
        }
        Random random = new Random();
        String randomId = String.valueOf(random.nextInt(1000));
        Student student = StudentConvert.INSTANCE.dto2domain(studentDTO);
        student.setId(randomId);
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
