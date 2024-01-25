package com.netdimen.agendaeditor.agenda.controllers;

import com.netdimen.agendaeditor.agenda.models.AgendaItem;
import com.netdimen.agendaeditor.agenda.repositories.AgendaItemRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/agendaItem")
public class AgendaItemController {
    private AgendaItemRepository agendaItemRepository;

    public AgendaItemController(AgendaItemRepository agendaItemRepository) {

        this.agendaItemRepository = agendaItemRepository;
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
    private ResponseEntity<Object> putAgenda(@PathVariable Long requestedId, @RequestBody AgendaItem agendaUpdate) {
        Optional<AgendaItem> optionalAgendaItem = agendaItemRepository.findById(requestedId);

        return optionalAgendaItem.map(agenda -> {
            AgendaItem updatedAgenda = new AgendaItem(
                    agenda.getId(),
                    agendaUpdate.getItemOrder(),
                    agendaUpdate.getPhase(),
                    agendaUpdate.getContent(),
                    agendaUpdate.getObjectives(),
                    agendaUpdate.getDuration(),
                    agendaUpdate.isCreditable(),
                    agendaUpdate.getAgenda());
            agendaItemRepository.save(updatedAgenda);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteAgenda(@PathVariable Long id) {

        if (!agendaItemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        agendaItemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
