package com.aaron.kata.babysitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;

@Controller
public class HoursCalculationController implements WebMvcConfigurer {

    private HoursCalculationService hoursCalculationService;

    @Autowired
    public HoursCalculationController(HoursCalculationService hoursCalculationService) {
        this.hoursCalculationService = hoursCalculationService;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }


    @GetMapping("/")
    public String index(HoursTrackingForm hoursTrackingForm) {
        return "index";
    }

    @PostMapping("/")
    public String submitHoursForCalculation(@Valid HoursTrackingForm hoursTrackingForm,
                                            BindingResult bindingResult,
                                            Model model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        String salaryResults = hoursCalculationService.calculateSalaryEarned(
                hoursTrackingForm.getStartTime(),
                hoursTrackingForm.getEndTime());

        model.addAttribute("salaryResults", salaryResults);
        return "results";
    }
}
