package com.example.validationdemo.service;

import com.example.validationdemo.model.RestResponseData;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppService {

    private Validator validator;
    RestTemplate restTemplate = new RestTemplate();

    public AppService() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public Collection<?> getIds(String id, BindingResult result) {

        RestResponseData restResponseData = restTemplate.getForObject("http://arest.me/api/sites/" + id, RestResponseData.class);

        Set<ConstraintViolation<RestResponseData>> violations = validator.validate(restResponseData);

        for (ConstraintViolation<RestResponseData> violation : violations) {

            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            // Add JSR-303 errors to BindingResult
            // This allows Spring to display them in view via a FieldError
            result.addError(new FieldError("restResponseData", propertyPath,
                    "Invalid " + propertyPath + "(" + message + ")"));
        }
        if (result.hasErrors()) {
            return result.getAllErrors();
        }

        Collection<RestResponseData> responseData = new ArrayList<>();
        responseData.add(restResponseData);
        return responseData;

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    Collection<?> exceptionHandler(MethodArgumentNotValidException e){
        Collection<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Collection<FieldError> errors = fieldErrors.stream().map(fieldError -> new FieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        return errors;
    }

}
