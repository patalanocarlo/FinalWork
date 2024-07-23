package Service;

import Entities.Utente;
import Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;


    public Utente createUtente(Utente utente) {
        return utenteRepository.save(utente);
    }


    public Optional<Utente> getUtenteById(Long id) {
        return utenteRepository.findById(id);
    }


    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }


    public Utente updateUtente(Long id, Utente utenteDetails) {
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));
        utente.setNome(utenteDetails.getNome());
        utente.setCognome(utenteDetails.getCognome());
        utente.setEmail(utenteDetails.getEmail());
        utente.setUsername(utenteDetails.getUsername());
        utente.setPassword(utenteDetails.getPassword());
        utente.setUtenteRuolo(utenteDetails.getUtenteRuolo());
        return utenteRepository.save(utente);
    }


    public void deleteUtente(Long id) {
        Utente utente = utenteRepository.findById(id).orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + id));
        utenteRepository.delete(utente);
    }


    public Optional<Utente> findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }
}
