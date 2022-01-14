package com.cg.controller.cp;

import com.cg.model.Customer;
import com.cg.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cp/customers")
public class CustomerCPController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ModelAndView showCustomerList() {
        List<Customer> customerList = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customers", customerList);
        modelAndView.setViewName("cp/customer/list");
        return modelAndView;
    }

    @GetMapping("/change-active/{id}")
    public ModelAndView showCustomerInfo(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<Customer> customer = customerService.findById(id);

        if (customer.isPresent()) {
            modelAndView.addObject("customer", customer.get());
            modelAndView.setViewName("cp/customer/change-active");
        }
        else {
            modelAndView.addObject("errorType", "404");
            modelAndView.addObject("errorMsg", "Http Error Code: 404. Resource not found");
            modelAndView.setViewName("errorPage");
        }
        return modelAndView;
    }

    @PostMapping("/change-active/{id}")
    public RedirectView changeActivationCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView();
        Optional<Customer> customerOptional = customerService.findById(id);
        if (customerOptional.isPresent()) {
            redirectView.setUrl("/cp/customers");
            String message;
            Customer customer = customerOptional.get();

            if (customer.isDeleted()) {
                customer.setDeleted(false);
                message = "Activation customer " + customer.getName() + " with Id: " + customer.getId() + " success";
            } else {
                customer.setDeleted(true);
                message = "Disable customer " + customer.getName() + " with Id: " + customer.getId() + " success";
            }
            customerService.save(customer);
            redirectAttributes.addFlashAttribute("success", message);

        } else {
            redirectView.setUrl("/error");
            redirectAttributes.addFlashAttribute("errorType", "404");
            redirectAttributes.addFlashAttribute("errorMsg", "Http Error Code: 404. Resource not found");
        }
        return redirectView;
    }

}
