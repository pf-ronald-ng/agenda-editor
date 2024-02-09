package com.netdimen.agendaeditor.agenda.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netdimen.agendaeditor.agenda.models.Agenda;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@Table(name = "agendaitem")
public class AgendaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int itemOrder;

    private String phase;

    private String content;

    private String objectives;

    private Long duration;


    private boolean creditable;

    @ManyToOne()
    @JsonIgnoreProperties("agendaItemList")
    private Agenda agenda;

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

}
