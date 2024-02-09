package com.netdimen.agendaeditor.controllers;

import com.netdimen.agendaeditor.agenda.models.Agenda;
import com.netdimen.agendaeditor.agenda.models.AgendaItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AgendaItemControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void shouldReturnAllTheItemsForAnAgenda(){
        ResponseEntity<List> response = restTemplate.getForEntity("/agendaItem/agenda/1", List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void shouldReturnASingleAgendaItem(){
        ResponseEntity<AgendaItem> response = restTemplate.getForEntity("/agendaItem/10", AgendaItem.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getPhase()).isEqualTo("Welcome");
    }
    @Test
    public void shouldNotReturnAnyAgendaItem(){
        ResponseEntity<AgendaItem> response = restTemplate.getForEntity("/agendaItem/123", AgendaItem.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    public void shouldCreateANewAgendaItem(){
        Agenda agenda = new Agenda(1L,"Agenda 1");
        AgendaItem newAgendaItem = new AgendaItem(9999L,
                1,
                "newly created item",
                "some content",
                "test",
                10L,
                true,
                agenda);

        ResponseEntity<Void> response = restTemplate.postForEntity("/agendaItem", newAgendaItem, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewAgendaItem = response.getHeaders().getLocation();
        ResponseEntity<AgendaItem> created = restTemplate.getForEntity(locationOfNewAgendaItem, AgendaItem.class);
        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(created.getBody()).isEqualToIgnoringGivenFields(newAgendaItem,"id","agenda.id");
    }
    @Test
    public void shouldEditAnAgendaItem(){

        Agenda agenda = new Agenda(1L,"Agenda 1");
        AgendaItem updateAgendaItem = new AgendaItem(9999L,
                3,
                "a new Phase",
                "updated content",
                "updated",
                100L,
                false,
                agenda);
        HttpEntity<AgendaItem> request = new HttpEntity<>(updateAgendaItem);
        ResponseEntity<Void> response = restTemplate.exchange("/agendaItem/2", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<AgendaItem> updated = restTemplate.getForEntity("/agendaItem/2", AgendaItem.class);
        assertThat(updated.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updated.getBody()).isEqualToIgnoringGivenFields(updateAgendaItem,"id","agenda.id");
    }

    @Test
    public void shouldNotUpdateAnAgendaItemThatDoesNotExist(){
        Agenda agenda = new Agenda(999L,"newName");
        AgendaItem updateAgendaItem = new AgendaItem(9999L,
                3,
                "a new Phase",
                "updated content",
                "updated",
                100L,
                false,
                agenda);
        HttpEntity<AgendaItem> request = new HttpEntity<>(updateAgendaItem);
        ResponseEntity<Void> response = restTemplate.exchange("/agenda/1234", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldDeleteAnAgendaItem(){
        ResponseEntity<Void> response = restTemplate.exchange("/agendaItem/8", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<AgendaItem> getResponse = restTemplate.getForEntity("/agendaItem/8", AgendaItem.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    public void shouldNotDeleteAnAgendaThatDoesNotExist(){
        ResponseEntity<Void> response = restTemplate.exchange("/agendaItem/11111", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
