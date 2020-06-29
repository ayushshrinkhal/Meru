package com.meru.meruzuulgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.meru.meruzuulgateway.filters.ErrorFilter;
import com.meru.meruzuulgateway.filters.PostFilter;
import com.meru.meruzuulgateway.filters.PreFilter;
import com.meru.meruzuulgateway.filters.RouteFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class MeruZuulGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeruZuulGatewayApplication.class, args);
	}

	@Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }
    
	@Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }
    
    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }
    
    @Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }
    
}
