import { Component } from '@angular/core';
import { AgendaDto } from '../dto/AgendaDto';
import { Subscription } from 'rxjs';
import { FindAgendasService } from './find-agendas.service';
import { HttpClientModule } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { AgendaItemDto } from '../dto/AgendaItemDto';
import { SaveAgendasComponent } from '../save-agendas/save-agendas.component';
import { SaveAgendaItemComponent } from '../save-agenda-item/save-agenda-item.component';
import { UpdateAgendaItemComponent } from '../update-agenda-item/update-agenda-item.component';
import { FormsModule } from '@angular/forms';
import { CdkDragDrop, CdkDropList, CdkDrag, moveItemInArray } from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-find-agendas',
  standalone: true,
  imports: [
    HttpClientModule,
    RouterLink,
    SaveAgendasComponent,
    SaveAgendaItemComponent,
    UpdateAgendaItemComponent,
    FormsModule,
    CdkDropList,
    CdkDrag
  ],
  providers: [FindAgendasService],
  templateUrl: './find-agendas.component.html',
  styleUrl: './find-agendas.component.scss'
})
export class FindAgendasComponent {
  public agendas: Array<AgendaDto>;
  public agendaItems: Array<AgendaItemDto> | null;
  public showAgendas: boolean;
  public showAgendaItemsTable: boolean;
  private subscription: Subscription;
  public agendaItemDto = new AgendaItemDto();
  public agenda: AgendaDto = new AgendaDto;
  public saveAgendaView: boolean;

  public saveAgendaItemView: boolean;
  public updateAgendaItemView: boolean;
  public totalCreditable: number;
  public size: number;
  public page: number;
  private lastPage: boolean;
  public totalPages: number;

  constructor(private _findAgendasService: FindAgendasService) {
    this.showAgendas = false;
    this.showAgendaItemsTable = false;
    this.agendas = [];
    this.subscription = new Subscription();
    this.agendaItems = [];
    this.saveAgendaView = false;

    this.saveAgendaItemView = false;
    this.updateAgendaItemView = false;
    this.totalCreditable = 0;
    this.size = 2;
    this.page = 0;
    this.lastPage = false;
    this.totalPages = 0;
  }

  findAgendasNext() {
    if(!this.lastPage){
      this.page += 1;
      this.findAgendas();
    }
  }

  findAgendasPrev() {
    if(this.page > 0){
      this.page -= 1;
      this.findAgendas();
    }
  }

  findAgendasFirst() {
    this.page = 0;
    this.findAgendas();
  }

  findAgendasLast() {
    this.page = this.totalPages - 1;
    this.findAgendas();
  }

  findAgendas() {
    if(this.size){
      this.subscription.add(
        this._findAgendasService.findAgendas(this.page, this.size).subscribe({
          next: (result) => {
            if (!result || result.length === 0) {
              this.handleNoAgendas();
              return;
            }
    
            this.agendas = result.content;
            this.lastPage = result.last;
            this.totalPages = result.totalPages;
            this.handleAgendasFound();
          },
          error: (error) => {
            console.error(error);
            this.handleServerError();
          }
        })
      );
    }
  }
  
  private handleNoAgendas() {
    this.showAgendas = false;
    alert('There are no records');
  }
  
  private handleAgendasFound() {
    this.showAgendas = true;
    this.resetAgendaItems();
    this.saveAgendaView = false;
  }
  
  private resetAgendaItems() {
    this.agendaItems = [];
    this.showAgendaItemsTable = false;
  }
  
  private handleServerError() {
    console.log('Server error');
  }

  showAgendaItems(agenda: AgendaDto){
    this.agenda.id = agenda.id;
    this.agenda.name = agenda.name;

    agenda.agendaItems?.map(item => {
      this.agendaItemDto = new AgendaItemDto();

      this.agendaItemDto.id = item.id;
      this.agendaItemDto.itemOrder = item.itemOrder;
      this.agendaItemDto.phase = item.phase;
      this.agendaItemDto.content = item.content;
      this.agendaItemDto.objectives = item.objectives;
      this.agendaItemDto.duration = item.duration;
      this.agendaItemDto.creditable = item.creditable;

      this.agendaItems?.push(this.agendaItemDto);
    });

    this.showAgendas = false;
    this.showAgendaItemsTable = true;
    this.saveAgendaView = false;
  }

  addAgenda(){
    this.showAgendas = false;
		this.showAgendaItemsTable = false;
    this.saveAgendaView = true;
    this.saveAgendaItemView = false;
    this.updateAgendaItemView = false;
  }

  deleteAgenda(agenda: AgendaDto){
    if(agenda.id){
      this.subscription.add(
        this._findAgendasService.deleteAgenda(agenda.id).subscribe({
          next: (result) => {    
            this.agendas = this.agendas?.filter(item => item !== agenda) ?? [];
            alert('Agenda deleted!!');
          },
          error: (error) => {
            console.error(error);
            this.handleServerError();
          }
        })
      );
    }
  }

  editAgendaItem(agendaItem: AgendaItemDto){
    this.agendaItemDto = { ...agendaItem };
		this.showAgendaItemsTable = false;
    this.saveAgendaView = false;
    this.updateAgendaItemView = true;
    this.saveAgendaItemView = false;
	}

  addAgendaItems(){
    this.agendaItemDto = new AgendaItemDto();
		this.showAgendaItemsTable = false;
    this.saveAgendaView = false;
    this.saveAgendaItemView = true;
    this.updateAgendaItemView = false;
  }

  deleteAgendaItem(agendaItem: AgendaItemDto){
    if(agendaItem.id){
      this.subscription.add(
        this._findAgendasService.deleteAgendaItem(agendaItem.id).subscribe({
          next: (result) => {    
            this.agendaItems = this.agendaItems?.filter(item => item !== agendaItem) ?? [];
            alert('Item deleted!!');
            this.showAgendas = false;
            this.showAgendaItemsTable = true;
            this.saveAgendaView = false;
          },
          error: (error) => {
            console.error(error);
            this.handleServerError();
          }
        })
      );
    }
	}

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  toBack(event: AgendaDto){
    this.agendas?.push(event);
    this.showAgendas = true;
    this.saveAgendaView = false;
    this.showAgendaItemsTable = false;
    this.saveAgendaItemView = false;
  }

  toBackFromUpdate(event: AgendaItemDto){
    this.agendaItems?.map(item => {
      if(item.id == event.id){
        item.itemOrder = event.itemOrder;
        item.phase = event.phase;
        item.content = event.content;
        item.objectives = event.objectives;
        item.duration = event.duration;
        item.creditable = event.creditable;
      }
    });

    this.saveAgendaView = false;
    this.showAgendaItemsTable = true;
    this.updateAgendaItemView = false;
  }

  toBackFromSave(event: AgendaItemDto){
    this.agendaItems?.push(event);
    this.saveAgendaView = false;
    this.showAgendaItemsTable = true;
    this.saveAgendaItemView = false;
  }

  cancel(){
    this.showAgendas = true;
		this.saveAgendaView = false;
    this.showAgendaItemsTable = false;
    this.saveAgendaItemView = false;
    this.updateAgendaItemView = false;
	}

  cancelItems(){
		this.saveAgendaView = false;
    this.showAgendaItemsTable = true;
    this.saveAgendaItemView = false;
    this.updateAgendaItemView = false;
	}

  getTotalDuration(): string {
    let totalDuration = 0;
    this.agendaItems?.map(item => {
      totalDuration += item.duration || 0;
    });

    return this.formatMinutesToHoursAndMinutes(totalDuration);
  }

  getTotalCreditableMinutes(): string {
    this.totalCreditable = 0;
    this.agendaItems?.filter(item => item.creditable).map(item => {
        this.totalCreditable += item.duration || 0;
      });

    return this.formatMinutesToHoursAndMinutes(this.totalCreditable);
  }

  formatMinutesToHoursAndMinutes(totalMinutes: number): string {
    const hours = Math.floor(totalMinutes / 60);
    const minutes = totalMinutes % 60;
  
    let formattedString = '';
  
    if (hours > 0) {
      formattedString += `${hours} ${hours === 1 ? 'hour' : 'hours'}`;
    }
  
    if (minutes > 0) {
      if (formattedString.length > 0) {
        formattedString += ' ';
      }
      formattedString += `${minutes} ${minutes === 1 ? 'minute' : 'minutes'}`;
    }
  
    return formattedString.trim();
  }

  onDrop(event: CdkDragDrop<AgendaItemDto[]>): void {
    moveItemInArray(this.agendaItems!, event.previousIndex, event.currentIndex);
  }
}
