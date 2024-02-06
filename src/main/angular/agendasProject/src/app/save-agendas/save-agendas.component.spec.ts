import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveAgendasComponent } from './save-agendas.component';

describe('SaveAgendasComponent', () => {
  let component: SaveAgendasComponent;
  let fixture: ComponentFixture<SaveAgendasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaveAgendasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SaveAgendasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
