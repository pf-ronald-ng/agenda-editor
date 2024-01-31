import { Component, Input } from '@angular/core';
import { AgendaDto } from '../../dto/AgendaDto';
import { AgendaItemDto } from '../../dto/AgendaItemDto';
import { FindAgendasService } from './find.agendas.service';

@Component({
  selector: 'find-agendas',
  templateUrl: './find.agendas.component.html',
  providers: [FindAgendasService]
})
export class FindAgendasComponent {
	public agendas:Array<AgendaDto>;
	public showAgendas:boolean;

	constructor(
		private _findAgendasService: FindAgendasService,
	){
		this.showAgendas = false;
		this.agendas = [];
	}

	findAgendas(){
		this._findAgendasService.findAgendas().subscribe(
			result => {
				this.agendas = result;

				if(this.agendas){
					if(this.agendas.length > 0){
						this.showAgendas = true;	
					}else{
						this.showAgendas = false;
						alert("No existen registros");
					}
                }else{
                	console.log("Error en el servidor");
                }
			},
			error => {
				console.log(<any>error);
			}
		);
	}

}