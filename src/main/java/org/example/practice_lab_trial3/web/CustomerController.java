package org.example.practice_lab_trial3.web;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.practice_lab_trial3.entities.Customer;
import org.example.practice_lab_trial3.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"a","e"})
@Controller
@AllArgsConstructor
public class CustomerController {
    static int num = 0;

    @Autowired
    private CustomerRepository customerRepository;
    @GetMapping(path = "/index")
    public String getCustomers(Model model) {
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("listCustomers",listCustomers);
        return "customers";
    }

    @GetMapping(path = "/formCustomers")
    public String getCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "formCustomers";
    }

    @PostMapping(path="/save")
    public String save(Model model, Customer customer, BindingResult bindingResult, ModelMap mm, HttpSession session) {
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getCustomerNumber());
        if (existingCustomer.isPresent()) {
            model.addAttribute("errorMessage", "The record you are trying to add is already existing. Choose a different customer number.");
            return "formCustomers"; // Return the form with an error message
        } else {
            customerRepository.save(customer);
            if (num == 2) {
                mm.put("e", 2);
                mm.put("a", 0);
            } else {
                mm.put("a", 1);
                mm.put("e", 0);
            }
            return "redirect:/index"; // Redirect to the customer list page after saving
        }
        /*if (bindingResult.hasErrors()) {
            return "formCustomers";
        } else {
            customerRepository.save(customer);
            if (num == 2) {
                mm.put("e", 2);
                mm.put("a", 0);
            } else {
                mm.put("a", 1);
                mm.put("e", 0);
            }
            return "redirect:index";
        }*/
    }
    @PostMapping(path="/edit")
    public String edit(Model model, Customer customer, BindingResult bindingResult, ModelMap mm, HttpSession session) {
        //Optional<Customer> existingCustomer = customerRepository.findById(customer.getCustomerNumber());
        if (bindingResult.hasErrors()) {
            //model.addAttribute("errorMessage", "The record you are trying to add is already existing. Choose a different customer number.");
            return "formCustomers"; // Return the form with an error message
        } else {
            customerRepository.save(customer);
            if (num == 2) {
                mm.put("e", 2);
                mm.put("a", 0);
            } else {
                mm.put("a", 1);
                mm.put("e", 0);
            }
            return "redirect:/index"; // Redirect to the customer list page after saving
        }
    }
    @GetMapping("/delete")
    public String delete(Long id){
        customerRepository.deleteById(id);
        return "redirect:/index";
    }
    @GetMapping("/editCustomers")
    public String editCustomers(Model model, Long id, HttpSession session){
        num = 2;
        session.setAttribute("info", 0);
        Customer customer = customerRepository.findById(id).orElse(null);
        if(customer==null) throw new RuntimeException("Customer does not exist");
        model.addAttribute("customer", customer);
        return "editCustomers";
    }
    @GetMapping("/projectedInvestment")
    public String getProjectedInvestment(Model model,Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            double interestRate = customer.getSavingsType().equals("Saving-Deluxe") ? 0.15 : 0.10;
            double startingAmount = customer.getCustomerDeposit();
            int numberOfYears = customer.getNumberOfYears();
            List<InvestmentProjection> projections = new ArrayList<>();

            for (int year = 1; year <= numberOfYears; year++) {
                double interest = startingAmount * interestRate;
                double endingBalance = startingAmount + interest;
                projections.add(new InvestmentProjection(year, startingAmount, interest, endingBalance));
                startingAmount = endingBalance;
            }

            model.addAttribute("customer", customer);
            model.addAttribute("projections", projections);
            return "projectedInvestment";
        } else {
            return "redirect:/index";
        }
    }
    public static class InvestmentProjection {
        private int year;
        private double startingAmount;
        private double interest;
        private double endingBalance;

        public InvestmentProjection(int year, double startingAmount, double interest, double endingBalance) {
            this.year = year;
            this.startingAmount = startingAmount;
            this.interest = interest;
            this.endingBalance = endingBalance;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public double getStartingAmount() {
            return startingAmount;
        }

        public void setStartingAmount(double startingAmount) {
            this.startingAmount = startingAmount;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            this.interest = interest;
        }

        public double getEndingBalance() {
            return endingBalance;
        }

        public void setEndingBalance(double endingBalance) {
            this.endingBalance = endingBalance;
        }
    }
}
