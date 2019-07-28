package com.oocl.ita.parkinglot.annotation;

import com.oocl.ita.parkinglot.enums.RoleEnum;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD,TYPE})
@Inherited
public @interface Auth {
    RoleEnum value();
}
