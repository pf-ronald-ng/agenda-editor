import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveAgendaItemComponent } from './save-agenda-item.component';

describe('SaveAgendaItemComponent', () => {
  let component: SaveAgendaItemComponent;
  let fixture: ComponentFixture<SaveAgendaItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaveAgendaItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SaveAgendaItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
