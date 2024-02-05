package com.netdimen.agendaeditor.agenda.model;

import com.netdimen.agendaeditor.agenda.model.dto.AgendaDto;
import com.netdimen.agendaeditor.agenda.model.dto.AgendaItemDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL)
    private List<AgendaItem> agendaItems = new ArrayList<>();

    public Agenda() {

    }

    public Agenda(String name, List<AgendaItem> agendaItems) {
        this.name = name;
        this.agendaItems = agendaItems;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AgendaItem> getAgendaItems() {
        return agendaItems;
    }

    public void setAgendaItems(List<AgendaItem> agendaItems) {
        this.agendaItems = agendaItems;
    }

    public void addAgendaItem(AgendaItem agendaItem) {
        agendaItems.add(agendaItem);
        agendaItem.setAgenda(this);
    }

    public void removeAgendaItem(AgendaItem agendaItem) {
        agendaItems.remove(agendaItem);
        agendaItem.setAgenda(null);
    }

    public AgendaDto mapToAgendaDto() {
        AgendaDto agendaDto = new AgendaDto();
        agendaDto.setId(this.id);
        agendaDto.setName(this.name);

        List<AgendaItemDto> agendaItemsDto = this.agendaItems.stream()
                .map(AgendaItem::mapToAgendaItemDto)
                .collect(Collectors.toList());

        agendaDto.setAgendaItems(agendaItemsDto);
        return agendaDto;
    }

}
