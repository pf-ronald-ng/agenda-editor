package com.netdimen.agendaeditor.agenda.rest;

import com.netdimen.agendaeditor.agenda.model.Agenda;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaDto;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaItemDto;
import com.netdimen.agendaeditor.agenda.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendas")
public class AgendaController {
    private final AgendaService agendaService;

    @Autowired
    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping
    public ResponseEntity<List<AgendaDto>> getAllAgendas() {
        List<AgendaDto> agendas = agendaService.getAllAgendas();
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
    public ResponseEntity<Agenda> createAgenda(@RequestBody AgendaDto agendaDto) {
        try {
            agendaService.createAgenda(agendaDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/{agendaId}/items")
    public ResponseEntity<Agenda> addAgendaItem(@PathVariable Long agendaId, @RequestBody AgendaItemDto agendaItemDto) {
        try {
            agendaService.addAgendaItem(agendaId, agendaItemDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*@GetMapping("/{agendaId}")
    public String showAgenda(@PathVariable Long agendaId, Model model) {
        Agenda agenda = agendaService.getAgendaById(agendaId);
        model.addAttribute("agenda", agenda);
        return "agenda";
    }

    @GetMapping("/{agendaId}/edit")
    public String editAgendaForm(@PathVariable Long agendaId, Model model) {
        Agenda agenda = agendaService.getAgendaById(agendaId);
        model.addAttribute("agenda", agenda);
        return "editAgenda";
    }

    @PostMapping("/{agendaId}/edit")
    public String editAgenda(@PathVariable Long agendaId, @ModelAttribute AgendaDto agendaDto) {
        agendaService.updateAgenda(agendaId, agendaDto);
        return "redirect:/agendas/" + agendaId;
    }

    @GetMapping("/{agendaId}/add-item")
    public String addItemForm(@PathVariable Long agendaId, Model model) {
        Agenda agenda = agendaService.getAgendaById(agendaId);
        model.addAttribute("agenda", agenda);
        model.addAttribute("agendaItem", new AgendaItemDto()); // Empty agenda item for the form
        return "addItem";
    }

    @PostMapping("/{agendaId}/add-item")
    public String addItem(
            @PathVariable Long agendaId,
            @ModelAttribute AgendaItemDto agendaItemDto,
            RedirectAttributes redirectAttributes
    ) {
        agendaService.addAgendaItem(agendaId, agendaItemDto);
        redirectAttributes.addFlashAttribute("message", "Agenda item added successfully!");
        return "redirect:/agendas/" + agendaId;
    }

    @GetMapping("/{agendaId}/delete-item/{itemId}")
    public String deleteItem(
            @PathVariable Long agendaId,
            @PathVariable Long itemId,
            RedirectAttributes redirectAttributes
    ) {
        agendaService.deleteAgendaItem(agendaId, itemId);
        redirectAttributes.addFlashAttribute("message", "Agenda item deleted successfully!");
        return "redirect:/agendas/" + agendaId;
    }

    @Transactional
    @PostMapping("/{agendaId}/save")
    public String saveAgenda(@PathVariable Long agendaId, RedirectAttributes redirectAttributes) {
        agendaService.saveAgenda(agendaId);
        redirectAttributes.addFlashAttribute("message", "Agenda saved successfully!");
        return "redirect:/agendas/" + agendaId;
    }*/

}
