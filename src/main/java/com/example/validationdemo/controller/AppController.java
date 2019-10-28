package com.example.validationdemo.controller;

import com.example.validationdemo.model.Car;
import com.example.validationdemo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
public class AppController {

    private Validator validator;

    @Autowired
    private AppService service;

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

    @RequestMapping("/ebro/{id}")
    public Collection<?> getIds(@RequestBody Object o, @PathVariable ("id") String id, BindingResult result) throws Exception {
        return service.getIds(id, result);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    Collection<?> exceptionHandler(MethodArgumentNotValidException e){
        Collection<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Collection<FieldError> errors = fieldErrors.stream().map(fieldError -> new FieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        return errors;
    }


    @RequestMapping("/x")
    public String getNumbers() {

        int[] lottery = new int[2];
        int randomNum;

        for (int i = 0; i < 2; i++) {
            randomNum = ThreadLocalRandom.current().nextInt(1, 13);
            for (int x = 0; x < i; x++) {
                if (lottery[x] == randomNum || lottery[x] == 0)
                {
                    randomNum = ThreadLocalRandom.current().nextInt(1, 13);
                    x = -1; // restart the loop
                }

            }
            lottery[i] = randomNum;
        }
        return Arrays.toString(lottery);
    }
}
