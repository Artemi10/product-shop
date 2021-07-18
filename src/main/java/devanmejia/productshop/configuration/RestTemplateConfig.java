package devanmejia.productshop.configuration;

import devanmejia.productshop.configuration.interceptors.RestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class RestTemplateConfig {
    @Autowired
    private RestTemplateInterceptor restTemplateInterceptor;

    @LoadBalanced
    @Bean("jwtToken")
    public RestTemplate getTokenRestEurekaTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(restTemplateInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Primary
    @LoadBalanced
    @Bean("loadBalanced")
    public RestTemplate getRestEurekaTemplate(){
        return new RestTemplate();
    }

    @Bean
    public RestTemplateInterceptor getRestTemplateInterceptor(){
        return new RestTemplateInterceptor();
    }

}
