package com.hellokoding.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @RequestMapping("/hello.html")
    public ModelAndView firstPage() {
        return new ModelAndView("hello");
    }

    @RequestMapping(path = "/run", method = RequestMethod.POST)
    public ModelAndView run(@RequestParam(value="command") String command) {
        ViscaController viscaController = new ViscaController();
        viscaController.open();
        String singleCommandResponse = viscaController.handleSingleCommand(command);
        System.out.println(command);
        viscaController.close();
        singleCommandResponse = singleCommandResponse.replaceFirst("^\\s*", "");
        ModelAndView hello = new ModelAndView("hello");
        hello.addObject("komentarz", singleCommandResponse);
        return hello;
    }

}
