package com.tarakshila;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/");
		resolver.setSuffix(".html");
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("css/**")

		.addResourceLocations("/WEB-INF/css/");
		
		registry.addResourceHandler("confirmation_css/**")

		.addResourceLocations("/WEB-INF/confirmation_css/");

		registry.addResourceHandler("dist/**")

		.addResourceLocations("/WEB-INF/dist/");
		registry.addResourceHandler("confirmation_dist/**")

		.addResourceLocations("/WEB-INF/confirmation_dist/");

		registry.addResourceHandler("confirmation_images/**")

		.addResourceLocations("/WEB-INF/confirmation_images/");
		registry.addResourceHandler("images/**")

		.addResourceLocations("/WEB-INF/images/");

		registry.addResourceHandler("scripts/**")

		.addResourceLocations("/WEB-INF/scripts/");
		registry.addResourceHandler("confirmation_scripts/**")

		.addResourceLocations("/WEB-INF/confirmation_scripts/");
		registry.addResourceHandler("template/**")

		.addResourceLocations("/WEB-INF/template/");
		registry.addResourceHandler("confirmation_template/**")

		.addResourceLocations("/WEB-INF/confirmation_template/");

		registry.addResourceHandler("app.js")

		.addResourceLocations("/WEB-INF/app.js");
		registry.addResourceHandler("confirmationapp.js")

		.addResourceLocations("/WEB-INF/confirmationapp.js");

		
		registry.addResourceHandler("console-sham.js")

		.addResourceLocations("/WEB-INF/console-sham.js");
		registry.addResourceHandler("angular.min.js")

		.addResourceLocations("/WEB-INF/angular.min.js");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}