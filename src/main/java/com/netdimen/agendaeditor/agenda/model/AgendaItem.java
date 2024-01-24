package com.netdimen.agendaeditor.agenda.model;

import com.netdimen.agendaeditor.agenda.model.dto.AgendaItemDto;

import javax.persistence.*;

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

    public Long getId() {
        return id;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public int getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(int itemOrder) {
        this.itemOrder = itemOrder;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public boolean isCreditable() {
        return creditable;
    }

    public void setCreditable(boolean creditable) {
        this.creditable = creditable;
    }

    public AgendaItemDto mapToAgendaItemDto() {
        AgendaItemDto agendaItemDto = new AgendaItemDto();
        agendaItemDto.setItemOrder(this.itemOrder);
        agendaItemDto.setPhase(this.phase);
        agendaItemDto.setContent(this.content);
        agendaItemDto.setObjectives(this.objectives);
        agendaItemDto.setDuration(this.duration);
        agendaItemDto.setCreditable(this.creditable);
        return agendaItemDto;
    }
}
