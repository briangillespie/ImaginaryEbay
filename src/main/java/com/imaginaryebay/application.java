package com.imaginaryebay;

import com.imaginaryebay.Configuration.*;
import com.imaginaryebay.Controller.ItemController;
import com.imaginaryebay.Controller.ItemControllerImpl;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.Repository.*;
import com.imaginaryebay.Models.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import com.imaginaryebay.Configuration.DatabaseConfiguration;
import com.imaginaryebay.Configuration.ModelConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Ben_Big on 6/27/16.
 */
public class application {
    public static void main(String[] args) {

        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DatabaseConfiguration.class, MailConfiguration.class, ModelConfiguration.class);

        //SendEmail sm = (SendEmail) applicationContext.getBean(SendEmail.class);
        //sm.sendEmailAccountCreation(new Userr("t_vivio@yahoo.com","Rahul","raughOZ:SRHGia;ro"));

        ItemRepository ir = applicationContext.getBean(ItemRepositoryImpl.class);
        System.out.println(ir.findAllItems());
    }
}