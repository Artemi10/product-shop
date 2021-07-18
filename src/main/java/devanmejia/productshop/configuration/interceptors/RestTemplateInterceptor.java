package devanmejia.productshop.configuration.interceptors;

import devanmejia.productshop.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        String bearerToken = jwtProvider.getBearerToken();
        if (bearerToken != null){
            headers.set("Authorization", jwtProvider.getBearerToken());
        }
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
