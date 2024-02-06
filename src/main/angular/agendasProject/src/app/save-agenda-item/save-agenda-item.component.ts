import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AgendaDto } from '../dto/AgendaDto';
import { AgendaItemDto } from '../dto/AgendaItemDto';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Router, RouterLink } from '@angular/router';
import { SaveAgendaItemService } from './save-agenda-item.service';

@Component({
  selector: 'app-save-agenda-item',
  standalone: true,
  imports: [
    FormsModule,
    HttpClientModule,
    RouterLink
  ],
  providers: [SaveAgendaItemService],
  templateUrl: './save-agenda-item.component.html',
  styleUrl: './save-agenda-item.component.scss'
})
export class SaveAgendaItemComponent {
  @Input('agendaSelected')
  agendaSelected: AgendaDto = new AgendaDto;
  agendaItemDto: AgendaItemDto;
  private agendaId: number;
  @Output() fromSaveAgendas = new EventEmitter();

  constructor(
    private _saveAgendaItemService: SaveAgendaItemService,
    private router: Router
	){
    this.agendaItemDto = new AgendaItemDto();
    this.agendaId = 0;
	}

  save(){
    this.agendaId = this.agendaSelected.id ?? 0;

    this.saveAgendaItem(this.agendaId, this.agendaItemDto);
  }

  private saveAgendaItem(agendaId: number, agendaItemDto: AgendaItemDto): void {
    this._saveAgendaItemService.saveAgendaItem(agendaId, agendaItemDto).subscribe({
      next: (result) => {
        alert('Item Created!!');       
        this.toBackFromSave(result);
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  toBackFromSave(agendaItemDto: AgendaItemDto){
		this.fromSaveAgendas.emit(agendaItemDto);
	}
}
