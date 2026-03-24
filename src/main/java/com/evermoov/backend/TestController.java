package com.evermoov.backend;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test() {
        return "OK";
    }

    @PostMapping("/api/calculate")
    public Map<String, Double> calculate(@RequestBody Map<String, Double> input) {

        double kezdo = input.get("kezdo");
        double futamido = input.get("futamido");
        double kamat = input.get("kamat");
        double maradvany = input.get("maradvany");

        double havi = (kezdo - maradvany) * (1 + kamat / 100) / futamido;

        Map<String, Double> result = new HashMap<>();
        result.put("havi", havi);

        return result;
    }
}
