import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { FormControl ,FormBuilder ,FormGroup,Validator, Validators} from '@angular/forms';
@Component({
  selector: 'my-nav',
  templateUrl: './my-nav.component.html',
  styleUrls: ['./my-nav.component.css']
})
export class MyNavComponent {
  privacyForm: FormGroup
  companyForm: FormGroup
  contactForm:FormGroup
  selectedTab:number;
  constructor(private fb: FormBuilder) 
  {
    this.privacyForm = this.fb.group({
      checkboxOne: ['', Validators.requiredTrue],
      checkboxTwo: ['', Validators.requiredTrue], // <--- the FormControl called "name"
    });

    this.companyForm = this.fb.group({
      companyName: ['', Validators.required],
      street: ['', Validators.required],
      no: ['', Validators.required],
      address: ['', Validators.required],
      zip: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required]
    });

    this.contactForm = this.fb.group({
      salutation: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      phone: ['', Validators.required]
    });
    this.selectedTab=0;
  }

  countries = [
    {value: 'Germany'},
    {value: 'India'}
  ];
  salutations = [
    {value: 'Mr'},
    {value: 'Mrs'}
  ];

  emailTemplates = [
    {value: 'Template1'},
    {value: 'Template2'}
  ];

  changeTabIndex(index): void {
    console.log('index...',index);
    this.selectedTab=index;
  }
}
