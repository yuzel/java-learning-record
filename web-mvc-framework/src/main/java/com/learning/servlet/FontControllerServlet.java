package com.learning.servlet;

import com.learning.controller.Controller;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class FontControllerServlet extends HttpServlet {
    
    private Map<String, Controller> controllerCache = new HashMap<>();
    
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        for (Controller controller: ServiceLoader.load(Controller.class)) {
            Class<?> clazz = controller.getClass();
            Path path = clazz.getAnnotation(Path.class);
            controllerCache.put(path.value(), controller);
        }
    }
    
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String requestMapping = StringUtils.substringAfter(requestURI, StringUtils.replace(req.getContextPath(), "//", "/"));
        Controller controller = controllerCache.get(requestMapping);
        if (controller != null) {
            try {
                controller.execute(req, res);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
