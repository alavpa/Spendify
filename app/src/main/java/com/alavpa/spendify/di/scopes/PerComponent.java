package com.alavpa.spendify.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by alavpa on 7/01/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerComponent {
}
