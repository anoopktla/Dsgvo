import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { FormControl, FormBuilder, FormGroup, FormArray, Validator, Validators } from '@angular/forms';
import { AdvService } from "../app.service";
@Component({
  selector: 'my-nav',
  templateUrl: './my-nav.component.html',
  styleUrls: ['./my-nav.component.css']
})
export class CreateAdvComponent {
  privacyForm: FormGroup
  companyForm: FormGroup
  contactForm: FormGroup
  emailInfoForm: FormGroup
  selectedTab: number;
  contractCategoryForm: FormGroup;
  adv: object;
  constructor(private fb: FormBuilder, private advService: AdvService) {
    this.privacyForm = this.fb.group({
      checkboxOne: ['', Validators.requiredTrue],
      checkboxTwo: ['', Validators.requiredTrue],
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
      email: ['',Validators.compose([
        Validators.required,
        Validators.email
      ])],
      phone: ['', Validators.required],
      position: []
    });

    this.contractCategoryForm = this.fb.group({
      categoryDetails: this.fb.array([this.initItemRows()]),
      validFrom:[''],
      validTo:[''],
      permanent:[''],
      isPhysicalAccess:[''],
      isLogicalAccess:[''],
      isDataAccess:[''],
      isDataTransfer:[''],
      isDataEntry:[''],
      controlOfProcessingRequired:[''],
      isAvailability:[''],
      isSeparation:[''],

      physicalAccessControl:[''],
      logicalAccessControl:[''],
      dataAccessControl:[''],
      dataTransferControl:[''],
      dataEntryControl:[''],
      processControl:[''],
      availabilityControl:[''],
      separationControl:['']

    });

    this.emailInfoForm = this.fb.group({
      cc: ['', Validators.email],
      bcc: ['', Validators.email],
      emailTemplate: ['']

    });
    this.selectedTab = 0;
    this.adv = {};
  }

  links = ['Settings', 'Create ADV', 'Email Template'];
  activeLink = this.links[0];

  initItemRows() {
    return this.fb.group({

      categoryOfData: [''],
      purposeOfCollection: [''],
      categoryOfSubjects: []

    });
  }

  addNewRow() {
    const control = <FormArray>this.contractCategoryForm.controls['categoryDetails'];

    control.push(this.initItemRows());
  }

  deleteRow(index: number) {

    const control = <FormArray>this.contractCategoryForm.controls['categoryDetails'];
    control.removeAt(index);
  }
  countries = [
    { value: 'Germany' },
    { value: 'India' }
  ];
  salutations = [
    { value: 'Mr' },
    { value: 'Mrs' }
  ];

  emailTemplates = [
    { value: 'English' },
    { value: 'German' }
  ];
  initDate = new Date();
  startDate = new Date(
    this.initDate.getFullYear(),
    this.initDate.getMonth(),
    this.initDate.getDate()
  );
  changeTabIndex(index): void {
    console.log(this.contactForm);
    this.selectedTab = index;
  }
  createAdv(): void {
    this.advService.createAdv(this.adv)
      .subscribe(data => {
        console.log('data...', data);
      });

  }
  generatePreview(): void {
    this.adv = {
      companyInfo: this.companyForm.value,
      personDetails: this.contactForm.value,
      emailDetails: this.emailInfoForm.value,
      contractInfo: this.contractCategoryForm.value
    }
    this.changeTabIndex(5);
  }

}
