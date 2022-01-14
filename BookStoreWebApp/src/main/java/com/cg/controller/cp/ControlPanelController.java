package com.cg.controller.cp;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cp")
public class ControlPanelController {


    @GetMapping("")
    public ModelAndView showCPHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cp/temp");
        return modelAndView;
    }

}
