package com.netdimen.agendaeditor.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaDto;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaItemDto;
import com.netdimen.agendaeditor.agenda.rest.AgendaController;
import com.netdimen.agendaeditor.agenda.service.AgendaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AgendaController.class)
public class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendaService agendaService;

    @Test
    public void testGetAllAgendas() throws Exception {
        List<AgendaDto> agendas = Arrays.asList(new AgendaDto(), new AgendaDto());
        PageImpl<AgendaDto> agendaPage = new PageImpl<>(agendas, PageRequest.of(0, 2), agendas.size());

        Mockito.when(agendaService.getAllAgendas(Mockito.anyInt(), Mockito.anyInt())).thenReturn(agendaPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/agendas"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void testGetAgenda() throws Exception {
        AgendaDto agendaDto = new AgendaDto();
        agendaDto.setId(1L);
        Mockito.when(agendaService.getAgendaById(Mockito.anyLong())).thenReturn(agendaDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/agendas/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void testCreateAgenda() throws Exception {
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

        AgendaDto agendaDtoResponse = new AgendaDto();
        agendaDtoResponse.setId(1L);
        agendaDtoResponse.setName("Test Agenda");

        Mockito.when(agendaService.createAgenda(agendaDtoRequest)).thenReturn(agendaDtoResponse);

        mockMvc.perform(post("/agendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(agendaDtoRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Agenda"));
    }

    @Test
    public void testAddAgendaItem() throws Exception {
        AgendaItemDto agendaItemDto = new AgendaItemDto();
        agendaItemDto.setId(1L);
        agendaItemDto.setItemOrder(1);
        agendaItemDto.setPhase("Welcome");
        agendaItemDto.setContent("");
        agendaItemDto.setObjectives("");
        agendaItemDto.setDuration(15L);
        agendaItemDto.setCreditable(true);


        Mockito.when(agendaService.addAgendaItem(Mockito.anyLong(), Mockito.any(AgendaItemDto.class))).thenReturn(agendaItemDto);

        mockMvc.perform(post("/agendas/1/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(agendaItemDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void testUpdateAgendaItem() throws Exception {
        AgendaDto agendaDto = new AgendaDto();
        Mockito.doNothing().when(agendaService).updateAgendaItem(Mockito.anyLong(), Mockito.any(AgendaItemDto.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/agendas/1/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(agendaDto)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testDeleteAgenda() throws Exception {
        Mockito.doNothing().when(agendaService).deleteAgenda(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/agendas/1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void testDeleteAgendaItem() throws Exception {
        Mockito.doNothing().when(agendaService).deleteAgendaItem(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/agendas/item/1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}
