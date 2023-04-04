package com.example.ruiji.comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class Exception {
//全局异常处理器
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //获取异常信息 Duplicate entry 'zhangsan' for key 'idx_username'
        log.info(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            //信息分段
            String[] strings = ex.getMessage().split("");
           return R.error(strings[2]+"已存在");
        }
        return R.error("失败");
    }
}
