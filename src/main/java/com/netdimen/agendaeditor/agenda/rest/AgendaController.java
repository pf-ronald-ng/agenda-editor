package com.netdimen.agendaeditor.agenda.rest;

import com.netdimen.agendaeditor.agenda.model.dto.AgendaDto;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaItemDto;
import com.netdimen.agendaeditor.agenda.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendas")
@CrossOrigin
public class AgendaController {
    private final AgendaService agendaService;

    @Autowired
    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping
    public ResponseEntity<Page<AgendaDto>> getAllAgendas(
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "size", defaultValue = "2") int size) {

        Page<AgendaDto> agendas = agendaService.getAllAgendas(page, size);
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/{agendaId}")
    public ResponseEntity<AgendaDto> getAgenda(@PathVariable Long agendaId) {
        try {
            AgendaDto agendaDto = agendaService.getAgendaById(agendaId);
            return ResponseEntity.ok(agendaDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<AgendaDto> createAgenda(@RequestBody AgendaDto agendaDto) {
        try {
            AgendaDto agendaDtoResult = agendaService.createAgenda(agendaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(agendaDtoResult);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/{agendaId}/item")
    public ResponseEntity<AgendaItemDto> addAgendaItem(@PathVariable Long agendaId, @RequestBody AgendaItemDto agendaItemDto) {
        try {
            AgendaItemDto agendaItemDtoResult = agendaService.addAgendaItem(agendaId, agendaItemDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(agendaItemDtoResult);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{agendaId}/item")
    public ResponseEntity<AgendaDto> updateAgendaItem(@PathVariable Long agendaId, @RequestBody AgendaItemDto agendaItemDto) {
        try {
            agendaService.updateAgendaItem(agendaId, agendaItemDto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{agendaId}")
    public ResponseEntity<AgendaDto> deleteAgenda(@PathVariable Long agendaId) {
        try {
            agendaService.deleteAgenda(agendaId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/item/{agendaItemId}")
    public ResponseEntity<AgendaDto> deleteAgendaItem(@PathVariable Long agendaItemId) {
        try {
            agendaService.deleteAgendaItem(agendaItemId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
