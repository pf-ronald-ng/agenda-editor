package com.netdimen.agendaeditor.agenda.repositories;

import com.netdimen.agendaeditor.agenda.models.AgendaItem;
import org.springframework.data.repository.CrudRepository;

public interface AgendaItemRepository extends CrudRepository<AgendaItem, Long> {

}
