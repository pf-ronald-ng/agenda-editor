import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateAgendaItemComponent } from './update-agenda-item.component';

describe('UpdateAgendaItemComponent', () => {
  let component: UpdateAgendaItemComponent;
  let fixture: ComponentFixture<UpdateAgendaItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateAgendaItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UpdateAgendaItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
