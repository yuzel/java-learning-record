package com.learning.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

@Path("/hello/world")
public class HelloWordController implements Controller {
    
    @Path("")
    @Override
    public void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Throwable {
        httpServletResponse.getWriter().write("hello world!");
    }
}
