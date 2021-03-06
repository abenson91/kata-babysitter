package com.aaron.kata.babysitter;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.LOCAL_VARIABLE})
@Constraint(validatedBy = InputHoursValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface InputHoursConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

