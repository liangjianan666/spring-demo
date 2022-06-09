package com.lja.config;

import com.lja.infrastructure.vo.StudentVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiangJianAn
 * @Description 日志切面
 * @Date 2022/5/26 14:43
 */

@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);


    @Pointcut("execution(* com.lja.service..*..list*(..))")
    void pre() {

    }

    @Pointcut("execution(* com.lja.service..*..add*(..))")
    void around() {

    }

    @Before("pre()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class returnType = signature.getReturnType();
        int modifiers = signature.getModifiers();
        String[] parameterNames = signature.getParameterNames();
        Class[] parameterTypes = signature.getParameterTypes();
        System.out.println("方法名：" + method.getName());
    }

    @Around("pre()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        StudentDTO studentDTO = (StudentDTO) joinPoint.getArgs()[0];
//        System.out.println("记录新增的学生日志" + studentDTO.toString());
        //有返回时，需 返回原方法的返回
        List<StudentVO> proceed;
        try {
            proceed = (ArrayList<StudentVO>) joinPoint.proceed();
        } catch (Exception e) {
            logger.error("异常信息： {}", e.getMessage());
            System.out.println("记录错误日志信息" + e.getMessage());
            throw e;
        }
        System.out.println("日志记录成功");
        return proceed;
    }
}
