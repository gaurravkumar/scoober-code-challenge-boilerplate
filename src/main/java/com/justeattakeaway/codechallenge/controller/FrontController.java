package com.justeattakeaway.codechallenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FrontController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/start-manual")
    public String startManual() {
        return "startManual";
    }
    @PostMapping("/startGame")
    public String startGame(
            @RequestParam("number") String number,
            @RequestParam("player1Name") String player1Name,
            @RequestParam("player2Name") String player2Name,
            @RequestParam("inputType") String inputType) {
        return "home";
    }
}