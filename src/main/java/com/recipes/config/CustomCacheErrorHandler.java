package com.recipes.config;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CustomCacheErrorHandler implements CacheErrorHandler {
	@Override
	public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
		log.info(String.format("Failure getting from cache: %s, exception: %s", cache.getName(), exception.toString()));
	}

	@Override
	public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
		log.info(String.format("Failure putting from cache: %s, exception: %s", cache.getName(), exception.toString()));
	}

	@Override
	public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
		log.info(
				String.format("Failure evicting from cache: %s, exception: %s", cache.getName(), exception.toString()));
	}

	@Override
	public void handleCacheClearError(RuntimeException exception, Cache cache) {
		log.info(
				String.format("Failure clearing from cache: %s, exception: %s", cache.getName(), exception.toString()));
	}
}