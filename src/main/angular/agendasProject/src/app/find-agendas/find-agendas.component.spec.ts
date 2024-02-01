import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindAgendasComponent } from './find-agendas.component';

describe('FindAgendasComponent', () => {
  let component: FindAgendasComponent;
  let fixture: ComponentFixture<FindAgendasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FindAgendasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FindAgendasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
