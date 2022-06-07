package com.lja.service;

import com.lja.infrastructure.dto.StudentDTO;
import com.lja.infrastructure.vo.StudentVO;

import java.util.List;

/**
 * @author liangjianan
 * @Description
 * @Date 2021/10/12 14:11
 */
public interface StudentService {
    List<StudentVO> listStudent();

    void add(StudentDTO studentDTO);

    void update(StudentDTO studentDTO);

    void delete(String id);
}
