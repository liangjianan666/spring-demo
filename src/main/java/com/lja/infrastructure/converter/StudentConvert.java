package com.lja.infrastructure.converter;

import com.lja.infrastructure.domain.Student;
import com.lja.infrastructure.dto.StudentDTO;
import com.lja.infrastructure.request.StudentRequest;
import com.lja.infrastructure.vo.StudentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liangjianan
 * @Description
 * @Date 2021/10/12 15:08
 */
@Mapper
public interface StudentConvert {
    StudentConvert INSTANCE = Mappers.getMapper(StudentConvert.class);

    StudentDTO request2dto(StudentRequest studentRequest);

    Student dto2domain(StudentDTO studentDTO);

    List<StudentVO> domain2voList(List<Student> studentList);

    StudentVO domain2vo(Student student);
}
