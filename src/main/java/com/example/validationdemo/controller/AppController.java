package com.example.validationdemo.controller;

import com.example.validationdemo.model.Car;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
public class AppController {

    private Validator validator;

    public AppController() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @RequestMapping(value = "cojones1", method = RequestMethod.POST)
    public Collection<?> setupCar(@RequestBody Car car, BindingResult result) {
        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        for (ConstraintViolation<Car> violation : violations) {

            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            // Add JSR-303 errors to BindingResult
            // This allows Spring to display them in view via a FieldError
            result.addError(new FieldError("car", propertyPath,
                    "Invalid " + propertyPath + "(" + message + ")"));
        }
        if (result.hasErrors()) {
            return result.getAllErrors();
        }

        Collection<Car> cars = new ArrayList<>();
        cars.add(car);
        return cars;
    }

    @RequestMapping(value = "cojones2", method = RequestMethod.POST)
    public Car getCojones2(@Valid @RequestBody Car car){
        return car;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    Collection<?> exceptionHandler(MethodArgumentNotValidException e){

        Collection<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Collection<FieldError> errors = fieldErrors.stream().map(fieldError -> new FieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        return errors;
    }

}
