package com.netdimen.agendaeditor.controllers;

import com.netdimen.agendaeditor.agenda.models.Agenda;
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
public class AgendaControllerTest {

    @Autowired
    TestRestTemplate restTemplate;
    @Test
    public void shouldReturnAllAgendas() {
        ResponseEntity<List> response = restTemplate.getForEntity("/agenda/", List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }
    @Test
    public void shouldReturnOneAgenda(){
        ResponseEntity<Agenda> response = restTemplate.getForEntity("/agenda/1", Agenda.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Agenda 1");
    }
    @Test
    public void shouldNotReturnAnyAgenda(){
        ResponseEntity<Agenda> response = restTemplate.getForEntity("/agenda/123", Agenda.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    public void shouldAddANewAgenda(){
        Agenda newAgenda = new Agenda(999L,"newAgendaTest");
        ResponseEntity<Void> response = restTemplate.postForEntity("/agenda", newAgenda, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewAgenda = response.getHeaders().getLocation();
        ResponseEntity<Agenda> getResponse = restTemplate.getForEntity(locationOfNewAgenda, Agenda.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo(newAgenda.getName());
    }

    @Test
    public void shouldEditAnAgenda(){
        Agenda updateAgenda = new Agenda(999L,"newName");
        HttpEntity<Agenda> request = new HttpEntity<>(updateAgenda);
        ResponseEntity<Void> response = restTemplate.exchange("/agenda/3", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Agenda> getResponse = restTemplate.getForEntity("/agenda/3", Agenda.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo(updateAgenda.getName());
    }

    @Test
    public void shouldNotUpdateAnAgendaThatDoesNotExist(){
        Agenda updateAgenda = new Agenda(999L,"newName");
        HttpEntity<Agenda> request = new HttpEntity<>(updateAgenda);
        ResponseEntity<Void> response = restTemplate.exchange("/agenda/1234", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    public void shouldDeleteAnAgenda(){
        ResponseEntity<Void> response = restTemplate.exchange("/agenda/5", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        ResponseEntity<Agenda> getResponse = restTemplate.getForEntity("/agenda/5", Agenda.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    @Test
    public void shouldNotDeleteAnAgendaThatDoesNotExist(){
        ResponseEntity<Void> response = restTemplate.exchange("/agenda/11111", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
