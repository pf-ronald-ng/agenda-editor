package com.netdimen.agendaeditor.agenda.controllers;

import com.netdimen.agendaeditor.agenda.models.Agenda.AgendaItem;
import com.netdimen.agendaeditor.agenda.repositories.AgendaItemRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/agendaItem")
public class AgendaItemController {
    private AgendaItemRepository agendaItemRepository;


    public AgendaItemController(AgendaItemRepository agendaItemRepository) {

        this.agendaItemRepository = agendaItemRepository;
    }
    @GetMapping("/agenda/{agendaId}")
    public ResponseEntity<List<AgendaItem>> index(@PathVariable long agendaId) {

        List<AgendaItem> result = agendaItemRepository.findByAgendaId(agendaId);
        return  ResponseEntity.ok(result);

    }
    @GetMapping("/{agendaItemId}")
    public ResponseEntity<AgendaItem> findById(@PathVariable Long agendaItemId) {
        Optional<AgendaItem> agendaItemOptional = agendaItemRepository.findById(agendaItemId);
        return agendaItemOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity<Void> createAgendaItem(@RequestBody AgendaItem newAgendaItemRequest, UriComponentsBuilder ucb) {
        AgendaItem agendaItem = new AgendaItem(
                newAgendaItemRequest.getItemOrder(),
                newAgendaItemRequest.getPhase(),
                newAgendaItemRequest.getContent(),
                newAgendaItemRequest.getObjectives(),
                newAgendaItemRequest.getDuration(),
                newAgendaItemRequest.isCreditable(),
                newAgendaItemRequest.getAgenda());
        AgendaItem savedAgenda = agendaItemRepository.save(agendaItem);
        URI locationOfNewCashCard = ucb
                .path("agendaItem/{id}")
                .buildAndExpand(savedAgenda.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Object> putAgendaItem(@PathVariable Long requestedId, @RequestBody AgendaItem agendaUpdate) {
        Optional<AgendaItem> optionalAgendaItem = agendaItemRepository.findById(requestedId);

        return optionalAgendaItem.map(agenda -> {
            Optional.ofNullable(agendaUpdate.getItemOrder()).ifPresent(agenda::setItemOrder);
            Optional.ofNullable(agendaUpdate.getPhase()).ifPresent(agenda::setPhase);
            Optional.ofNullable(agendaUpdate.getContent()).ifPresent(agenda::setContent);
            Optional.ofNullable(agendaUpdate.getObjectives()).ifPresent(agenda::setObjectives);
            Optional.ofNullable(agendaUpdate.getDuration()).ifPresent(agenda::setDuration);
            Optional.ofNullable(agendaUpdate.isCreditable()).ifPresent(agenda::setCreditable);
            Optional.ofNullable(agendaUpdate.getAgenda()).ifPresent(agenda::setAgenda);
            agendaItemRepository.save(agenda);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteAgendaItem(@PathVariable Long id) {

        if (!agendaItemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        agendaItemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
