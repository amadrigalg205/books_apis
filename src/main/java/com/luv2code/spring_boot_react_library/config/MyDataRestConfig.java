package com.luv2code.spring_boot_react_library.config;

import com.luv2code.spring_boot_react_library.entity.Book;
import com.luv2code.spring_boot_react_library.entity.Message;
import com.luv2code.spring_boot_react_library.entity.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    //@Value("${cross.origin.link}")
    //private String theAllowedOrigins  = "https://luvtoread.vercel.app";

    @Override
    public  void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {
                HttpMethod.POST,
                HttpMethod.PATCH,
                HttpMethod.DELETE,
                HttpMethod.PUT};

        config.exposeIdsFor(Book.class);
        config.exposeIdsFor(Review.class);
        config.exposeIdsFor(Message.class);

        disableHttpMethods(Book.class, config, theUnsupportedActions);
        disableHttpMethods(Review.class, config, theUnsupportedActions);
        disableHttpMethods(Message.class, config, theUnsupportedActions);

        /* Configure CORS mapping */
        cors.addMapping(config.getBasePath() + "/**")
                .allowedOrigins("https://luvtoread.vercel.app")
                .allowedHeaders("*");
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) ->
                        httpMethods.disable(theUnsupportedActions));
    }
}
