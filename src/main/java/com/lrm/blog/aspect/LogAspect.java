package com.lrm.blog.aspect;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
//Aspect注解使其能够进行切面操作，Component注解开启组件扫描,才能扫描到LogAspect

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //注解Pointcut定义了切面,*.*(..)代表web包下任何类的任何参数方法、类、控制器、语言都拦截掉
    @Pointcut("execution(* com.lrm.blog.web.*.*(..))")
    public void log() {

    }

    //在切面执行之前做些东西,注解Before("log()")是log切面之前执行
    //JoinPoint可以获取切面的类名、方法
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        String classMethod=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url,ip,classMethod,args);
//        logger.info("--------------doBefore-------------------");
        logger.info("Request : {}",requestLog);
    }

    //在切面执行之后做些东西,注解After("log()")是log切面之后执行
    @After("log()")
    public void doAfter() {
//        logger.info("--------------doAfter-------------------");
    }

    //返回内容
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("Result : {}", result);
    }

    //定义内部类来记录日志打印各种信息
    private class RequestLog {
        private String url;
        private String ip;
        private String className;
        private Object[] args;
        public RequestLog(String url, String ip, String className, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.className = className;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", className='" + className + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
