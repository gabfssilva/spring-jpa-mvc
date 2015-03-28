package br.com.spring.jpa.mvc.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Controller
public class ControllerExample {


    @RequestMapping("/secure/*")
    public ModelAndView secure(){
        ModelAndView modelAndView = new ModelAndView("secure/secure");
        return modelAndView;
    }

    @RequestMapping("/*")
    public ModelAndView unsecure(){
        ModelAndView modelAndView = new ModelAndView("unsecure");
        return modelAndView;
    }
}
