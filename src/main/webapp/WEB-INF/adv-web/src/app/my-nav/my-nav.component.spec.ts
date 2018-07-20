
import { fakeAsync, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAdvComponent } from './my-nav.component';

describe('CreateAdvComponent', () => {
  let component: CreateAdvComponent;
  let fixture: ComponentFixture<CreateAdvComponent>;

  beforeEach(fakeAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAdvComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateAdvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should compile', () => {
    expect(component).toBeTruthy();
  });
});
