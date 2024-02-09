package com.netdimen.agendaeditor.agenda.repositories;

import com.netdimen.agendaeditor.agenda.models.Agenda;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgendaRepository extends PagingAndSortingRepository<Agenda, Long> {
}
