package com.netdimen.agendaeditor.agenda.repositories;

import com.netdimen.agendaeditor.agenda.models.AgendaItem;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AgendaItemRepository extends CrudRepository<AgendaItem, Long> {
    List<AgendaItem> findByAgendaId(long agendaId);

}
