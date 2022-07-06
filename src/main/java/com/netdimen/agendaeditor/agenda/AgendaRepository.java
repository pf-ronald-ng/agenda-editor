package com.netdimen.agendaeditor.agenda;

import com.netdimen.agendaeditor.agenda.Agenda;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AgendaRepository extends PagingAndSortingRepository<Agenda, Long> {

}
