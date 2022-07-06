package com.netdimen.agendaeditor.agenda;

import com.netdimen.agendaeditor.agenda.AgendaItem;
import org.springframework.data.repository.CrudRepository;

public interface AgendaItemRepository extends CrudRepository<AgendaItem, Long> {

}
