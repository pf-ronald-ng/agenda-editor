package com.netdimen.agendaeditor.agenda.models;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "agenda")
public class Agenda {

    @Getter
    @Id
    @GeneratedValue
    private Long id;

    @Getter
    private String name;

    @OneToMany(mappedBy = "agenda")
    private List<AgendaItem> agendaItemList = new ArrayList<>();

    private Agenda() {

    }

    public Agenda(String name) {
        this.name = name;
    }

    public Agenda(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
