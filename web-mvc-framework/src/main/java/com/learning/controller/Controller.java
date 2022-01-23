package com.learning.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    
    void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Throwable;
}
