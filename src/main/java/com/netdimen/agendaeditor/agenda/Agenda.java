package com.netdimen.agendaeditor.agenda;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "agenda")
public class Agenda {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "agenda")
    private List<AgendaItem> agendaItemList = new ArrayList<>();

    private Agenda() {

    }

    public Agenda(String name) {
        this.name = name;
    }

}
