package com.hoaxify.ws.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({FIELD})
@Retention(RUNTIME)

public @interface UniqueEmail {

    String message() default "{hoaxify.constraint.email.notunique}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


}
