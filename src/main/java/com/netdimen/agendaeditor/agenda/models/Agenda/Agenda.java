package com.netdimen.agendaeditor.agenda.models.Agenda;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgendaItem> agendaItemList = new ArrayList<>();

    private String owner;

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
