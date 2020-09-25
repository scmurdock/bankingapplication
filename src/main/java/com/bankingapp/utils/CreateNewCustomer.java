package com.bankingapp.utils;

import akka.dispatch.sysmsg.Create;
import com.bankingapp.model.Customer;
import com.google.gson.Gson;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Created by Mandy on 10/4/2016.
 */
public class CreateNewCustomer {

    private static Gson gson = new Gson();
    private static JedisClient jedisClient = new JedisClient();
    private static Logger logger = Logger.getLogger(CreateNewCustomer.class.getName());

    public static String createCustomer(Customer newCustomer) throws Exception{
        List<Customer> customers = JedisData.getEntityList(Customer.class);
        Predicate<Customer> findExistingCustomerPredicate = customer -> customer.getEmail().equals(newCustomer.getEmail());
        Optional<Customer> matchingCustomer = customers.stream().filter(findExistingCustomerPredicate).findAny();

        if (matchingCustomer.isPresent()){
            throw new Exception("Customer already exists");
        }

        if (newCustomer != null && !newCustomer.getCustomerName().isEmpty()) {
            //SAVE USER TO REDIS
            logger.info("Saving new Customer to Redis: "+gson.toJson(newCustomer));
            JedisData.loadToJedis(newCustomer, Customer.class);
        }
        return "Welcome: " + newCustomer.getCustomerName();
    }

}
