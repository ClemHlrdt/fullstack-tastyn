package com.clemhlrdt.recipeapp.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;

/*
	Annotation to access the currently authenticated user in the controller
	It is a wrapper from @AuthenticationPrincipal
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}