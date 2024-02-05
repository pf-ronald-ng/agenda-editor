package com.netdimen.agendaeditor.agenda.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AgendaItemDto {
    private Long id;
    @NonNull private int itemOrder;
    @NonNull private String phase;
    private String content;
    private String objectives;
    @NonNull private Long duration;
    private boolean creditable;
}
