import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTrophyComponent } from './add-trophy.component';

describe('AddTrophyComponent', () => {
  let component: AddTrophyComponent;
  let fixture: ComponentFixture<AddTrophyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddTrophyComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddTrophyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
