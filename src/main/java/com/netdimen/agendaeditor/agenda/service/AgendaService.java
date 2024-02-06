package com.netdimen.agendaeditor.agenda.service;

import com.netdimen.agendaeditor.agenda.model.Agenda;
import com.netdimen.agendaeditor.agenda.model.AgendaItem;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaDto;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaItemDto;
import com.netdimen.agendaeditor.agenda.repository.AgendaItemRepository;
import com.netdimen.agendaeditor.agenda.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;
    private final AgendaItemRepository agendaItemRepository;

    private static final String AGENDA_NOT_FOUND = "Agenda not found with id: ";

    @Autowired
    public AgendaService(AgendaRepository agendaRepository, AgendaItemRepository agendaItemRepository) {
        this.agendaRepository = agendaRepository;
        this.agendaItemRepository = agendaItemRepository;
    }

    public Page<AgendaDto> getAllAgendas(int page, int size) {
        return agendaRepository.findAll(PageRequest.of(page, size)).map(Agenda::mapToAgendaDto);
    }

    public AgendaDto getAgendaById(Long agendaId) {
        return agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("Agenda not found")).mapToAgendaDto();
    }

    @Transactional
    public AgendaDto createAgenda(AgendaDto agendaDto) {
        validateAgendaDto(agendaDto);

        Agenda agenda = new Agenda();
        agenda.setName(agendaDto.getName());

        AgendaDto agendaDtoResult = agendaRepository.save(agenda).mapToAgendaDto();

        List<AgendaItemDto> agendaItemsDto = createAgendaItems(agendaDto.getAgendaItems(), agenda);

        agendaDtoResult.setAgendaItems(agendaItemsDto);

        return agendaDtoResult;
    }

    @Transactional
    public Agenda updateAgenda(Long agendaId, AgendaDto agendaDto) {
        validateAgendaDto(agendaDto);

        Agenda existingAgenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new RuntimeException(AGENDA_NOT_FOUND + agendaId));

        existingAgenda.setName(agendaDto.getName());

        agendaItemRepository.deleteByAgenda(existingAgenda);

        createAgendaItems(agendaDto.getAgendaItems(), existingAgenda);

        agendaRepository.save(existingAgenda);

        return existingAgenda;
    }

    public AgendaItemDto addAgendaItem(Long agendaId, AgendaItemDto agendaItemDto) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException(AGENDA_NOT_FOUND + agendaId));

        AgendaItem agendaItem = new AgendaItem();

        agendaItem.setItemOrder(agendaItemDto.getItemOrder());
        agendaItem.setPhase(agendaItemDto.getPhase());
        agendaItem.setContent(agendaItemDto.getContent());
        agendaItem.setObjectives(agendaItemDto.getObjectives());
        agendaItem.setDuration(agendaItemDto.getDuration());
        agendaItem.setCreditable(agendaItemDto.isCreditable());

        agendaItem.setAgenda(agenda);

        return agendaItemRepository.save(agendaItem).mapToAgendaItemDto();
    }

    public void updateAgendaItem(Long agendaId, AgendaItemDto agendaItemDto) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException(AGENDA_NOT_FOUND + agendaId));

        AgendaItem existingAgendaItem = agendaItemRepository.findById(agendaItemDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("AgendaItem not found with id: " + agendaItemDto.getId()));

        existingAgendaItem.setItemOrder(agendaItemDto.getItemOrder());
        existingAgendaItem.setPhase(agendaItemDto.getPhase());
        existingAgendaItem.setContent(agendaItemDto.getContent());
        existingAgendaItem.setObjectives(agendaItemDto.getObjectives());
        existingAgendaItem.setDuration(agendaItemDto.getDuration());
        existingAgendaItem.setCreditable(agendaItemDto.isCreditable());

        existingAgendaItem.setAgenda(agenda);

        agendaItemRepository.save(existingAgendaItem);
    }

    @Transactional
    public void deleteAgenda(Long agendaId) {
        Agenda agendaToDelete = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new IllegalArgumentException("Agenda not found"));

        agendaToDelete.getAgendaItems().forEach(agendaItemRepository::delete);

        agendaRepository.deleteById(agendaId);
    }

    public void deleteAgendaItem(Long agendaItemId) {
        AgendaItem existingAgendaItem = agendaItemRepository.findById(agendaItemId)
                .orElseThrow(() -> new IllegalArgumentException("AgendaItem not found with id: " + agendaItemId));

        agendaItemRepository.deleteById(existingAgendaItem.getId());
    }

    private void validateAgendaDto(AgendaDto agendaDto) {
        if (agendaDto == null) {
            throw new IllegalArgumentException("AgendaDto must not be null.");
        }
    }

    private List<AgendaItemDto> createAgendaItems(List<AgendaItemDto> agendaItemsDto, Agenda agenda) {
        for (AgendaItemDto agendaItemDto : agendaItemsDto) {
            validateAgendaItemDto(agendaItemDto);

            AgendaItem agendaItem = new AgendaItem(
                    agendaItemDto.getItemOrder(),
                    agendaItemDto.getPhase(),
                    agendaItemDto.getContent(),
                    agendaItemDto.getObjectives(),
                    agendaItemDto.getDuration(),
                    agendaItemDto.isCreditable(),
                    agenda
            );

            agendaItem = agendaItemRepository.save(agendaItem);

            agendaItemDto.setId(agendaItem.getId());
        }

        return agendaItemsDto;
    }

    private void validateAgendaItemDto(AgendaItemDto agendaItemDto) {
        if (agendaItemDto == null) {
            throw new IllegalArgumentException("AgendaItemDto must not be null.");
        }
    }
}
