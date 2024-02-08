package ru.povarchuk.springcourse.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
Вместо web.xml файла
 */
public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException { // запускается при старте Spring приложения
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext); // выполняется приватный метод
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, true, "/*"); //добавляем к нашем приложения один фильтр HiddenHttpMethodFilter
        // будет перенаправляить HTTP запросы на нужный метод контроллера (@PatchMapping)
        // "/*" гарантирует что будет работать для всех адресов в приложении
        // в Spring Boot оба абзаца в одну строку умещаются
    }
}
