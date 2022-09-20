package top.xing.duqingplus.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xing.duqingplus.until.BackInfoUntil;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public BackInfoUntil<String> myExceptionHandler(IllegalArgumentException e){
        return new BackInfoUntil<>("400","参数异常",e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BackInfoUntil<String> ExceptionHandler(Exception e){
        System.out.println(e.getMessage());
        return new BackInfoUntil<>("500","服务器异常",e.getMessage());
    }
}
