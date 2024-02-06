import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AgendaDto } from '../dto/AgendaDto';
import { AgendaItemDto } from '../dto/AgendaItemDto';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { UpdateAgendaItemService } from './update.agenda.item.service';

@Component({
  selector: 'app-update-agenda-item',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    RouterLink
  ],
  providers: [UpdateAgendaItemService],
  templateUrl: './update-agenda-item.component.html',
  styleUrl: './update-agenda-item.component.scss'
})
export class UpdateAgendaItemComponent {
  @Input('agendaSelected')
  agendaSelected: AgendaDto = new AgendaDto;
  @Input('agendaItemSelected')
  agendaItemDto: AgendaItemDto;
  private agendaId: number;
  @Output() fromSaveAgendas = new EventEmitter();

  constructor(
    private _saveAgendasService: UpdateAgendaItemService,
    private router: Router
	){
    this.agendaItemDto = new AgendaItemDto();
    this.agendaId = 0;
	}

  update(){
    this.agendaId = this.agendaSelected.id ?? 0;

    this.updateAgendaItem(this.agendaId, this.agendaItemDto);
      
  }

  private updateAgendaItem(agendaId: number, agendaItemDto: AgendaItemDto): void {
    this._saveAgendasService.updateAgendaItem(agendaId, agendaItemDto).subscribe({
      next: (result) => {
        alert('Item updated!!');
        this.toBackFromUpdate();        
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  toBackFromUpdate(){
		this.fromSaveAgendas.emit(this.agendaItemDto);
	}
}
