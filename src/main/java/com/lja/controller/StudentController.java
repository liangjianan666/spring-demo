package com.lja.controller;

import com.lja.infrastructure.common.Result;
import com.lja.infrastructure.converter.StudentConvert;
import com.lja.infrastructure.dto.StudentDTO;
import com.lja.infrastructure.request.StudentRequest;
import com.lja.infrastructure.vo.StudentVO;
import com.lja.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author liangjianan
 * @Description
 * @Date 2021/10/12 11:35
 */
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    private Result<List<StudentVO>> list() {
        List<StudentVO> voList = studentService.listStudent();
        return Result.success(voList);
    }

    @PostMapping("/add")
    private Result<Void> add(@RequestBody StudentRequest studentRequest) {
        StudentDTO studentDTO = StudentConvert.INSTANCE.request2dto(studentRequest);
        studentService.add(studentDTO);
        return Result.success();
    }

    @PostMapping("/update")
    private Result<Void> update(@RequestBody StudentRequest studentRequest) {
        Optional.ofNullable(studentRequest.getId()).orElseThrow(() -> new RuntimeException("id不能为空"));
        StudentDTO studentDTO = StudentConvert.INSTANCE.request2dto(studentRequest);
        studentService.update(studentDTO);
        return Result.success();
    }

    @GetMapping("/delete")
    private Result<Void> delete(String id) {
        studentService.delete(id);
        return Result.success();
    }

}
