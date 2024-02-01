import { Component } from '@angular/core';
import { AgendaDto } from '../dto/AgendaDto';
import { Subscription } from 'rxjs';
import { FindAgendasService } from './find-agendas.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-find-agendas',
  standalone: true,
  imports: [HttpClientModule],
  providers: [FindAgendasService],
  templateUrl: './find-agendas.component.html',
  styleUrl: './find-agendas.component.scss'
})
export class FindAgendasComponent {
  public agendas: Array<AgendaDto>;
  public showAgendas: boolean;
  private subscription: Subscription;

  constructor(private _findAgendasService: FindAgendasService) {
    this.showAgendas = false;
    this.agendas = [];
    this.subscription = new Subscription();
  }

  findAgendas() {
    this.subscription.add(
      this._findAgendasService.findAgendas().subscribe({
        next: (result) => {
          this.agendas = result;

          if (this.agendas) {
            if (this.agendas.length > 0) {
              this.showAgendas = true;
            } else {
              this.showAgendas = false;
              alert('No existen registros');
            }
          } else {
            console.log('Error en el servidor');
          }
        },
        error: (error) => {
          console.error(error);
        }
      })
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
