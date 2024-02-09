package com.netdimen.agendaeditor.agenda.controllers;

import com.netdimen.agendaeditor.agenda.models.Agenda;
import com.netdimen.agendaeditor.agenda.repositories.AgendaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/agenda")
public class AgendaController {

    private AgendaRepository agendaRepository;

    public AgendaController(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Agenda>> index() {
        List<Agenda> result = new ArrayList<>();
        agendaRepository.findAll().forEach(result::add);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{agendaId}")
    public ResponseEntity<Agenda> findById(@PathVariable Long agendaId) {
        Optional<Agenda> agendaOptional = agendaRepository.findById(agendaId);
        return agendaOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity<Void> createAgenda(@RequestBody Agenda newAgendaRequest, UriComponentsBuilder ucb) {
        Agenda agenda = new Agenda(newAgendaRequest.getName());
        Agenda savedAgenda = agendaRepository.save(agenda);
        URI locationOfNewCashCard = ucb
                .path("agenda/{id}")
                .buildAndExpand(savedAgenda.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Object> putAgenda(@PathVariable Long requestedId, @RequestBody Agenda agendaUpdate) {
        Optional<Agenda> optionalAgenda = agendaRepository.findById(requestedId);

        return optionalAgenda.map(agenda -> {
            Agenda updatedAgenda = new Agenda(agenda.getId(), agendaUpdate.getName());
            agendaRepository.save(updatedAgenda);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteAgenda(@PathVariable Long id) {

        if (!agendaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        agendaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
