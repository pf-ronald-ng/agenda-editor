import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AgendaDto } from '../dto/AgendaDto';
import { AgendaItemDto } from '../dto/AgendaItemDto';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { SaveAgendasService } from './save-agendas.service';

@Component({
  selector: 'app-save-agendas',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    RouterLink,
    SaveAgendasComponent
  ],
  providers: [SaveAgendasService],
  templateUrl: './save-agendas.component.html',
  styleUrl: './save-agendas.component.scss'
})
export class SaveAgendasComponent {
  agenda: AgendaDto = new AgendaDto;
  agendaItemDto: AgendaItemDto;
  @Output() fromSaveAgendas = new EventEmitter();

  constructor(
    private _saveAgendasService: SaveAgendasService,
    private router: Router
	){
    this.agenda = new AgendaDto();
    this.agendaItemDto = new AgendaItemDto();
	}

  save(){
    this.agenda.agendaItems = [];
    this.agenda.agendaItems?.push(this.agendaItemDto);
    this.saveAgenda(this.agenda);
  }

  private saveAgenda(agendaDto: AgendaDto): void {
    this._saveAgendasService.saveAgenda(agendaDto).subscribe({
      next: (result) => {
        alert('Agenda Created!!');
        this.toBack(result);
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  toBack(agendaDto: AgendaDto){
		this.fromSaveAgendas.emit(agendaDto);
	}
  
}
