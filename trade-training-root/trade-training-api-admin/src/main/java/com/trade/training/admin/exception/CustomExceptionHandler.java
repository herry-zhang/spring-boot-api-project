package com.trade.training.admin.exception;

import com.trade.training.model.dto.response.BaseResponseDTO;
import com.trade.training.common.exception.CustomException;
import com.trade.training.config.ErrorCode;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 * @author herry-zhang
 * @date 2018-07-18 23:20:00
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     *
     * @param e 异常
     * @return 通用返回对象
     */
    @ExceptionHandler(CustomException.class)
    public BaseResponseDTO handleCustomException(CustomException e) {
        return BaseResponseDTO.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理路径不存在异常
     *
     * @param e 异常
     * @return 通用返回对象
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResponseDTO handlerNoFoundException(NoHandlerFoundException e) {
        return BaseResponseDTO.error(ErrorCode.SC_NOT_FOUND.getCode(), ErrorCode.SC_NOT_FOUND.getMessage());
    }

    /**
     * 处理参数类型异常
     *
     * @param e 异常
     * @return 通用返回对象
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public BaseResponseDTO handleAuthorizationException(Exception e) {
        return BaseResponseDTO.error(ErrorCode.SYSTEM_ARGUMENT_TYPE_MISMATCH.getCode(), ErrorCode.SYSTEM_ARGUMENT_TYPE_MISMATCH.getMessage() + "：" + e.getMessage());
    }

    /**
     * 处理没有权限异常
     *
     * @param e 异常
     * @return 通用返回对象
     */
    @ExceptionHandler(AuthorizationException.class)
    public BaseResponseDTO handleAuthorizationException(AuthorizationException e) {
        return BaseResponseDTO.error(ErrorCode.SYS_USER_UNAUTHORIZED.getCode(), ErrorCode.SYS_USER_UNAUTHORIZED.getMessage());
    }

    /**
     * 处理通用异常
     *
     * @param e 异常
     * @return 通用返回对象
     */
    @ExceptionHandler(Exception.class)
    public BaseResponseDTO handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return BaseResponseDTO.error(e.getMessage());
    }
}
