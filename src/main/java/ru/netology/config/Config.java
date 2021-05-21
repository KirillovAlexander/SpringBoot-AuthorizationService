package ru.netology.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.netology.model.User;

import java.util.List;

@Configuration
@Validated
public class Config implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserResolver());
    }

    private class UserResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter methodParameter) {
            return methodParameter.hasParameterAnnotation(UserAnnotation.class);
        }

        @Override
        public User resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception{
            String name = nativeWebRequest.getParameter("user");
            String password = nativeWebRequest.getParameter("password");
            return new User(name, password);
            //User user = new User(name, password);
           // WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, user, "customResolver");
//            binder.validate();
//            if (binder.getBindingResult().hasErrors()) {
//                throw new MethodArgumentNotValidException(methodParameter, binder.getBindingResult());
//            }
//            return user;
        }
    }
}
