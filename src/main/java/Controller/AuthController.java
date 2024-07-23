package Controller;

import Entities.Utente;
import Service.UtenteService;
import Configuration.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTools jwtTools;


    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Utente utente) {

        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        Utente nuovoUtente = utenteService.createUtente(utente);


        String token = jwtTools.createToken(nuovoUtente);


        Map<String, String> response = new HashMap<>();
        response.put("message", "Utente registrato con successo!");
        response.put("token", token);
        return response;
    }


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Utente loginRequest) {
        Utente utente = utenteService.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato con username: " + loginRequest.getUsername()));


        if (!passwordEncoder.matches(loginRequest.getPassword(), utente.getPassword())) {
            throw new RuntimeException("Password errata");
        }


        String token = jwtTools.createToken(utente);


        Map<String, String> response = new HashMap<>();
        response.put("message", "Login effettuato con successo!");
        response.put("token", token);
        return response;
    }
}
