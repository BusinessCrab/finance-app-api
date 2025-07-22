package com.finance.FinancialApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ServerController{
    public ServerController() {}

    @GetMapping
    public String showInfo() {
        return "Server is working!";
    }
}