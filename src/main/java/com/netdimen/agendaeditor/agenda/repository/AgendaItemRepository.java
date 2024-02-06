package com.netdimen.agendaeditor.agenda.repository;

import com.netdimen.agendaeditor.agenda.model.Agenda;
import com.netdimen.agendaeditor.agenda.model.AgendaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaItemRepository extends JpaRepository<AgendaItem, Long> {

    void deleteByAgenda(Agenda agenda);
}
