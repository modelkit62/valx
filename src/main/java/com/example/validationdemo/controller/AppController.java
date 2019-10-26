package com.example.validationdemo.controller;

import com.example.validationdemo.model.Car;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/app")
public class AppController {

    private Validator validator;

    public AppController() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<ObjectError> setupCar(@RequestBody Car car, BindingResult result) {
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
        return null;
    }
}
