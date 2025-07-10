package com.recipes.util.retry;

import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Retryable(
    value = {java.sql.SQLException.class, org.hibernate.HibernateException.class},
    maxAttempts = 5,
    backoff = @Backoff(delay = 2000, multiplier = 2)
)
public @interface DatabaseRetryable {
}