package com.wyfx.upms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by liuxingyu on 2017/2/6 0006.
 */

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    public Object defaultExceptionHandler(HttpServletRequest req, Exception e, HttpServletResponse response) throws IOException {
//        if(e.getCause()instanceof IllegalStateException){
//            PrintWriter writer = response.getWriter();
//            writer.append(new ResponseBody(Constants.EMPTY_ARRAY).toString());
//
//        }
        e.printStackTrace();
        logger.debug("globalException:" + e.getMessage());
        System.err.print(e.toString());
        return null;
    }
}
