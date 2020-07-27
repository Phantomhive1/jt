package com.jt.aop;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice //advice通知
@Slf4j
public class SystemExceptionAOP {

    @ExceptionHandler({RuntimeException.class})
    public Object systemResultException(HttpServletRequest request, Exception e) {
        String callback = request.getParameter("callback");
        if (StringUtils.isEmpty(callback)) {
            log.error("~~~~~"+e.getMessage()+"}", e);
            return SysResult.fail();
        }

        String method = request.getMethod();
        if (!method.isEmpty()) {
            log.error("~~~~~"+e.getMessage()+"}", e);
            return SysResult.fail();
        }

        log.error("~~~~~"+e.getMessage()+"}", e);
        return new JSONPObject(callback, SysResult.fail());
    }
}
