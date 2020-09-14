package com.pianorang.money.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ResponseBody
@ControllerAdvice
public class SprinkleExceptionAdvice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = NoSprinkleMoneyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto noSprinkleMoneyException(NoSprinkleMoneyException e){
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


    @ExceptionHandler(value = InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto invalidParameterException(InvalidParameterException e){
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto invalidTokenException(InvalidTokenException e){
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = ExpiredTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto expiredTokenException(ExpiredTokenException e){
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = InvalidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto invalidUserException(InvalidUserException e){
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto numberFormatException(MethodArgumentTypeMismatchException e){
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), "파라미터 형식이 잘못됐습니다.");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDto sprinkleExceptionHandler(Exception e) {
        logger.debug(e.getMessage());
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), "예외가 발생했습니다. 관리자에게 문의해 주세요.");
    }


}
