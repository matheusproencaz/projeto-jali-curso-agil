import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/UserService/user.service';
import { UserUpdate } from 'src/app/shared/User';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  user: UserUpdate;

  constructor(private fb: FormBuilder,
              private userService: UserService) {
    this.createForm();
  }

  settingForm: FormGroup;

  @ViewChild("sForm") settingFormDirective: any;

  formErrors: any = {
    'userName': '',
    'oldPassword': '',
    'newPassword': '',
  }

  validationMessages: any = {
    'userName': {
      'required':'Digite o nome de usuário',
      'minlength': 'Nome de usuário precisa ter mais do que 5 caracteres!',
      'maxlength': 'Nome de usuário precisa ter menos do que 32 caracteres!',
    },
    'oldPassword': {
      'required' : 'Digite sua antiga senha!',
    },
    'newPassword': {
      'required' : 'Digite uma nova senha!',
      'minlength': 'Senha precisa ter mais do que 8 caracteres!',
      'maxlength': 'Senha precisa ter menos do que 120 caracteres!!',
    },
  };

  ngOnInit(): void {
    this.createForm();
    
  }

  createForm(): void{
    this.settingForm = this.fb.group({
      userName: ["", [Validators.required, Validators.minLength(5), Validators.maxLength(32)]],
      oldPassword: ["", Validators.required],
      newPassword: ["", [Validators.required, Validators.minLength(8), Validators.maxLength(120)]]
    })

    this.settingForm.valueChanges.subscribe(data => this.onValueChanged(data));
    
    this.onValueChanged();
  }

  onValueChanged(data?: any){
    if(!this.settingForm) return;
    const form = this.settingForm;
    for(const field in this.formErrors){
      if(this.formErrors.hasOwnProperty(field)) {
        // Clear Previous error message (if any)
        this.formErrors[field] = '';

        const control = form.get(field);
        if(control && control.dirty && !control.valid){
          const messages = this.validationMessages[field];

          for(const key in control.errors){
            if(control.errors.hasOwnProperty(key)){
              this.formErrors[field] += messages[key] + ' ';
            }
          }
        }
      }
    }
  }

  onSubmit(){
    this.userService.getYourself()
      .subscribe((user) => {
        this.user = this.settingForm.value;
        this.user.id = user.id

        this.userService.updateUser(this.user)
            .subscribe(() => {
              alert("Usuário alterado com sucesso!")
              this.settingFormDirective.resetForm()
              this.settingForm.reset({
                userName: '',
                oldPassword: '',
                newPassword: ''
              })
            })
      });
  }
}
