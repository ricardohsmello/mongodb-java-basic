package br.com.ricas.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrestacoesController {

    @GetMapping("/greeting")
    public String test() {
        return "ola";
    }

}
