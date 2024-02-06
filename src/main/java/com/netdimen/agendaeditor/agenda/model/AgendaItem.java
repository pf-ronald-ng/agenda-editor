package com.netdimen.agendaeditor.agenda.model;

import com.netdimen.agendaeditor.agenda.model.dto.AgendaItemDto;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "agendaitem")
public class AgendaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Agenda agenda;

    private int itemOrder;

    private String phase;

    private String content;

    private String objectives;

    private Long duration;

    private boolean creditable;

    public AgendaItem() {

    }

    public AgendaItem(int itemOrder, String phase, String content, String objectives, Long duration, boolean creditable, Agenda agenda) {
        this.itemOrder = itemOrder;
        this.phase = phase;
        this.content = content;
        this.objectives = objectives;
        this.duration = duration;
        this.creditable = creditable;
        this.agenda = agenda;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public void setItemOrder(int itemOrder) {
        this.itemOrder = itemOrder;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public void setCreditable(boolean creditable) {
        this.creditable = creditable;
    }

    public AgendaItemDto mapToAgendaItemDto() {
        AgendaItemDto agendaItemDto = new AgendaItemDto();
        agendaItemDto.setId(this.id);
        agendaItemDto.setItemOrder(this.itemOrder);
        agendaItemDto.setPhase(this.phase);
        agendaItemDto.setContent(this.content);
        agendaItemDto.setObjectives(this.objectives);
        agendaItemDto.setDuration(this.duration);
        agendaItemDto.setCreditable(this.creditable);
        return agendaItemDto;
    }
}
