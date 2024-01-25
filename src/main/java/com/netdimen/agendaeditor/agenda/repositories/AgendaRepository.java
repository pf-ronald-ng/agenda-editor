package com.netdimen.agendaeditor.agenda.repositories;

import com.netdimen.agendaeditor.agenda.models.Agenda;
import com.netdimen.agendaeditor.agenda.models.AgendaItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgendaRepository extends PagingAndSortingRepository<Agenda, Long> {
}
