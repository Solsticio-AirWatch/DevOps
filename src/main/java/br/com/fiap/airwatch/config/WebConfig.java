package br.com.fiap.airwatch.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;
@Configuration
@EnableSpringDataWebSupport
public class WebConfig implements WebMvcConfigurer {
    @Override public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver r=new PageableHandlerMethodArgumentResolver();
        r.setMaxPageSize(100); r.setFallbackPageable(PageRequest.of(0,20)); resolvers.add(r);
    }
}
