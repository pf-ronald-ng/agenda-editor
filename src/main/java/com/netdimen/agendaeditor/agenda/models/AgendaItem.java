package com.netdimen.agendaeditor.agenda.models;

import lombok.Data;

import javax.persistence.*;

@Data
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

    private AgendaItem() {

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

    public AgendaItem(long id ,int itemOrder, String phase, String content, String objectives, Long duration, boolean creditable, Agenda agenda) {
        this.id = id;
        this.itemOrder = itemOrder;
        this.phase = phase;
        this.content = content;
        this.objectives = objectives;
        this.duration = duration;
        this.creditable = creditable;
        this.agenda = agenda;
    }

}
