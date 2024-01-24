package com.netdimen.agendaeditor.agenda.service;

import com.netdimen.agendaeditor.agenda.model.Agenda;
import com.netdimen.agendaeditor.agenda.model.AgendaItem;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaDto;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaItemDto;
import com.netdimen.agendaeditor.agenda.repository.AgendaItemRepository;
import com.netdimen.agendaeditor.agenda.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final AgendaItemRepository agendaItemRepository;

    @Autowired
    public AgendaService(AgendaRepository agendaRepository, AgendaItemRepository agendaItemRepository) {
        this.agendaRepository = agendaRepository;
        this.agendaItemRepository = agendaItemRepository;
    }

    public List<AgendaDto> getAllAgendas() {
        return agendaRepository.findAll().stream()
                .map(Agenda::mapToAgendaDto)
                .collect(Collectors.toList());
    }

    public AgendaDto getAgendaById(Long agendaId) {
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("Agenda not found")).mapToAgendaDto();
    }

    @Transactional
    public void createAgenda(AgendaDto agendaDto) {
        validateAgendaDto(agendaDto);

        //Agenda agenda = Agenda.builder().name(agendaDto.getName()).build();
        Agenda agenda = new Agenda();
        agenda.setName(agendaDto.getName());

        agendaRepository.save(agenda);

        createAgendaItems(agendaDto.getAgendaItems(), agenda);
    }

    @Transactional
    public Agenda updateAgenda(Long agendaId, AgendaDto agendaDto) {
        // Validate input data
        validateAgendaDto(agendaDto);

        // Retrieve the existing agenda entity
        Agenda existingAgenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new RuntimeException("Agenda not found with id: " + agendaId));

        // Update agenda properties
        existingAgenda.setName(agendaDto.getName());

        // Delete existing agenda items
        agendaItemRepository.deleteByAgenda(existingAgenda);

        // Create and associate updated agenda items
        createAgendaItems(agendaDto.getAgendaItems(), existingAgenda);

        // Save the updated agenda entity to the database
        agendaRepository.save(existingAgenda);

        return existingAgenda;
    }

    @Transactional
    public void addAgendaItem(Long agendaId, AgendaItemDto agendaItemDto) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("Agenda not found with id: " + agendaId));

        AgendaItem agendaItem = new AgendaItem();

        agendaItem.setItemOrder(agendaItemDto.getItemOrder());
        agendaItem.setPhase(agendaItemDto.getPhase());
        agendaItem.setContent(agendaItemDto.getContent());
        agendaItem.setObjectives(agendaItemDto.getObjectives());
        agendaItem.setDuration(agendaItemDto.getDuration());
        agendaItem.setCreditable(agendaItemDto.isCreditable());

        agendaItem.setAgenda(agenda);

        agendaItemRepository.save(agendaItem);

        agenda.getAgendaItems().add(agendaItem);
        agendaRepository.save(agenda);
    }

    @Transactional
    public void deleteAgenda(Long agendaId) {
        // Ensure that the cascade delete is performed for associated AgendaItems
        Agenda agendaToDelete = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("Agenda not found"));
        agendaToDelete.getAgendaItems().forEach(agendaItemRepository::delete);

        // Delete the Agenda
        agendaRepository.deleteById(agendaId);
    }

    private void validateAgendaDto(AgendaDto agendaDto) {
        // Implement validation logic for AgendaDto
        if (agendaDto == null) {
            throw new IllegalArgumentException("AgendaDto must not be null.");
        }
    }

    private void createAgendaItems(List<AgendaItemDto> agendaItemDtos, Agenda agenda) {
        for (AgendaItemDto agendaItemDto : agendaItemDtos) {
            // Validate agenda item data
            validateAgendaItemDto(agendaItemDto);

            // Create agenda item entity
            AgendaItem agendaItem = new AgendaItem(
                    agendaItemDto.getItemOrder(),
                    agendaItemDto.getPhase(),
                    agendaItemDto.getContent(),
                    agendaItemDto.getObjectives(),
                    agendaItemDto.getDuration(),
                    agendaItemDto.isCreditable(),
                    agenda
            );

            // Save agenda item entity to the database
            agendaItemRepository.save(agendaItem);
        }
    }

    private void validateAgendaItemDto(AgendaItemDto agendaItemDto) {
        // Implement validation logic for AgendaItemDto
        if (agendaItemDto == null) {
            throw new IllegalArgumentException("AgendaItemDto must not be null.");
        }
    }
}
