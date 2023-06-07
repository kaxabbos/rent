package com.rent.controller;

import com.rent.controller.main.Attributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
public class StatisticsCont extends Attributes {
    @GetMapping
    public String Statistics(Model model) {
        AddAttributesStatistics(model);
        return "statistics";
    }
}
