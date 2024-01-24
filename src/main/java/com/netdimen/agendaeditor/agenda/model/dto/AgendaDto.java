package com.netdimen.agendaeditor.agenda.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class AgendaDto {
    @NonNull private String name;
    @NonNull private List<AgendaItemDto> agendaItems;
}
