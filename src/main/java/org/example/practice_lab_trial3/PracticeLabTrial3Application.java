package org.example.practice_lab_trial3;

import org.example.practice_lab_trial3.entities.Customer;
import org.example.practice_lab_trial3.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class PracticeLabTrial3Application {

    public static void main(String[] args) {

        SpringApplication.run(PracticeLabTrial3Application.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return args -> {
            customerRepository.deleteAll();
            customerRepository.save(new Customer(115L, "Jasper", 15000, 5, "Saving-Deluxe"));
            customerRepository.save(new Customer(112L, "Zanip", 5000, 2, "Saving-Deluxe"));
            customerRepository.save(new Customer(113L, "Geronima", 6000, 5, "Saving-Regular"));
            customerRepository.findAll().forEach(p->{
                System.out.println(p.getCustomerName());
            });
        };
    }

}
