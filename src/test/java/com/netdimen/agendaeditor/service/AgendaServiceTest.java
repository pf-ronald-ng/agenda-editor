package com.netdimen.agendaeditor.service;

import com.netdimen.agendaeditor.agenda.model.Agenda;
import com.netdimen.agendaeditor.agenda.model.AgendaItem;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaDto;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaItemDto;
import com.netdimen.agendaeditor.agenda.repository.AgendaItemRepository;
import com.netdimen.agendaeditor.agenda.repository.AgendaRepository;
import com.netdimen.agendaeditor.agenda.service.AgendaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private AgendaItemRepository agendaItemRepository;

    @InjectMocks
    private AgendaService agendaService;

    @Test
    void testGetAllAgendas() {
        Agenda agenda = new Agenda();
        agenda.setName("Test Agenda");

        Page<Agenda> agendaPage = new PageImpl<>(Collections.singletonList(agenda));

        Mockito.when(agendaRepository.findAll(Mockito.any(Pageable.class))).thenReturn(agendaPage);

        agendaService.getAllAgendas(0, 2);

        assertNotNull("Post shouldn't be null", agendaPage);
        assertThat(agendaPage.getContent().size(), equalTo(1));
    }

    @Test
    void testGetAgendaById() {
        Agenda agenda = new Agenda();
        agenda.setId(1L);
        agenda.setName("Test Agenda");
        Mockito.when(agendaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(agenda));

        AgendaDto agendaDto = agendaService.getAgendaById(1L);

        assertNotNull("Post shouldn't be null", agendaDto);
        assertThat(agendaDto.getId(), equalTo(1L));
    }

    @Test
    void testCreateAgenda() {
        AgendaDto agendaDtoRequest = new AgendaDto();
        agendaDtoRequest.setName("Test Agenda");

        AgendaItemDto agendaItemDto = new AgendaItemDto();
        agendaItemDto.setItemOrder(1);
        agendaItemDto.setPhase("Welcome");
        agendaItemDto.setContent("");
        agendaItemDto.setObjectives("");
        agendaItemDto.setDuration(15L);
        agendaItemDto.setCreditable(true);

        List<AgendaItemDto> agendaItemDtoList = new ArrayList<>();
        agendaItemDtoList.add(agendaItemDto);

        agendaDtoRequest.setAgendaItems(agendaItemDtoList);

        Agenda agendaRequest = new Agenda();
        agendaRequest.setId(1L);
        agendaRequest.setName("Test Agenda");

        AgendaItem agendaItem = new AgendaItem();
        agendaItem.setId(1L);
        agendaItem.setItemOrder(1);
        agendaItem.setPhase("Welcome");
        agendaItem.setContent("");
        agendaItem.setObjectives("");
        agendaItem.setDuration(15L);
        agendaItem.setCreditable(true);

        List<AgendaItem> agendaItemList = new ArrayList<>();
        agendaItemList.add(agendaItem);

        agendaRequest.setAgendaItems(agendaItemList);

        Mockito.when(agendaRepository.save(Mockito.any(Agenda.class))).thenReturn(agendaRequest);
        Mockito.when(agendaItemRepository.save(Mockito.any(AgendaItem.class))).thenReturn(agendaItem);

        AgendaDto agendaDto = agendaService.createAgenda(agendaDtoRequest);

        assertThat(agendaDto.getId(), equalTo(1L));
        assertThat(agendaDto.getName(), equalTo("Test Agenda"));
    }

    @Test
    void testAddAgendaItem() {
        Agenda agenda = new Agenda();
        agenda.setId(1L);

        AgendaItemDto agendaItemDto = new AgendaItemDto();
        agendaItemDto.setItemOrder(1);
        agendaItemDto.setPhase("Test Phase");

        AgendaItem agendaItem = new AgendaItem();
        agendaItem.setId(1L);
        agendaItem.setItemOrder(1);
        agendaItem.setPhase("Welcome");
        agendaItem.setContent("");
        agendaItem.setObjectives("");
        agendaItem.setDuration(15L);
        agendaItem.setCreditable(true);

        Mockito.when(agendaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(agenda));
        Mockito.when(agendaItemRepository.save(Mockito.any(AgendaItem.class))).thenReturn(agendaItem);

        AgendaItemDto agendaItemDtoResult = agendaService.addAgendaItem(1L, agendaItemDto);

        assertThat(agendaItemDtoResult.getItemOrder(), equalTo(1));
        assertThat(agendaItemDtoResult.getPhase(), equalTo("Welcome"));
    }

    @Test
    void testUpdateAgendaItem() {
        Agenda agenda = new Agenda();
        agenda.setId(1L);

        AgendaItemDto agendaItemDto = new AgendaItemDto();
        agendaItemDto.setId(1L);
        agendaItemDto.setItemOrder(1);
        agendaItemDto.setPhase("Updated Test Phase");

        AgendaItem existingAgendaItem = new AgendaItem();
        existingAgendaItem.setId(1L);
        existingAgendaItem.setAgenda(agenda);

        Mockito.when(agendaRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(agenda));
        Mockito.when(agendaItemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingAgendaItem));

        agendaService.updateAgendaItem(1L, agendaItemDto);
    }
}
