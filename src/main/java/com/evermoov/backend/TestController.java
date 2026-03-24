package com.evermoov.backend;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // CORS miatt
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    @PostMapping("/calculate")
    public Map<String, Object> calculate(@RequestBody Map<String, Object> data) {

        double kezdo = ((Number) data.get("kezdo")).doubleValue();
        int futamido = ((Number) data.get("futamido")).intValue();
        double kamat = ((Number) data.get("kamat")).doubleValue();
        double maradvany = ((Number) data.get("maradvany")).doubleValue();

        double haviKamat = kamat / 100 / 12;

        double hitel = kezdo - maradvany;

        double havi = (hitel * haviKamat) /
                (1 - Math.pow(1 + haviKamat, -futamido));

        double tartozas = hitel;

        List<Map<String, Object>> tabla = new ArrayList<>();

        LocalDate datum = LocalDate.now();

        for (int i = 0; i < futamido; i++) {

            double kamatResz = tartozas * haviKamat;
            double tokeResz = havi - kamatResz;

            tartozas -= tokeResz;

            Map<String, Object> sor = new HashMap<>();
            sor.put("datum", datum.toString());
            sor.put("toke", Math.round(tokeResz));
            sor.put("kamat", Math.round(kamatResz));
            sor.put("torleszto", Math.round(havi));

            tabla.add(sor);

            datum = datum.plusMonths(1);
        }

        return Map.of(
                "havi", Math.round(havi),
                "tabla", tabla
        );
    }
}
