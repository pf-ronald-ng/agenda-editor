package com.netdimen.agendaeditor.agenda;

import com.netdimen.agendaeditor.agenda.model.Agenda;
import com.netdimen.agendaeditor.agenda.model.AgendaItem;
import com.netdimen.agendaeditor.agenda.repository.AgendaItemRepository;
import com.netdimen.agendaeditor.agenda.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final AgendaRepository agendaRepository;

    private final AgendaItemRepository agendaItemRepository;

    @Autowired
    public DatabaseLoader(AgendaRepository agendaRepository, AgendaItemRepository agendaItemRepository) {
        this.agendaRepository = agendaRepository;
        this.agendaItemRepository = agendaItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        createAgendaWithItem(1);
        createAgendaWithItem(2);
        createAgendaWithItem(3);
        createAgendaWithItem(4);
        createAgendaWithItem(5);
    }

    private void createAgendaWithItem(int count) {
        Agenda agenda = new Agenda();
        agenda.setName("Agenda " + count);
        AgendaItem item = new AgendaItem(1, "Welcome", "", "", 15l, false, agenda);
        agendaRepository.save(agenda);
        agendaItemRepository.save(item);
    }
}
