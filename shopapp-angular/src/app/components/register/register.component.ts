import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import { RegisterDTO } from '../../dtos/user/register.dto';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm;
  phoneNumber: string;
  password: string;
  retypePassword: string;
  fullName: string;
  address: string;
  isAccepted: boolean;
  dateOfBirth: Date;

  constructor( private router: Router, private userService: UserService) {
    this.phoneNumber = '';
    this.password = '';
    this.retypePassword = '';
    this.fullName = '';
    this.dateOfBirth = new Date();
    this.address = '';
    this.isAccepted = true;
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
  }

  onPhoneNumberChange() {
    console.log(`Phone type : ${this.phoneNumber}`);
  }

  register() {
    const message =
      `phone : ${this.phoneNumber}` +
      `password : ${this.password}` +
      `rytypePassword : ${this.retypePassword}` +
      `fullName : ${this.fullName}` +
      `dateOfBirth : ${this.dateOfBirth}` +
      `adress : ${this.address}` +
      `isAccepted : ${this.isAccepted}`;

    //alert(message);
    debugger
    
    const registerDTO:RegisterDTO = {
      fullname: this.fullName,
      phone_number: this.phoneNumber,
      address: this.address,
      password: this.password,
      retype_password: this.retypePassword,
      date_of_birth: this.dateOfBirth,
      facebook_account_id: 0,
      google_account_id: 0,
      role_id: 1,
    };
    this.userService.register(registerDTO).subscribe({
      next: (response: any) => {
        debugger
        this.router.navigate(['/login']);
        //Xu ly ket qua tra ve khi dang ky thanh cong
        // if(response && (response.status === 200 || response.status === 201)){
          
        // }else {
        //   //xu ly neu dang ky khong thanh cong
        // }
      },
      complete: () =>{
        debugger
      },
      error: (error: any) => {
        alert(`Cannot register, error : ${error.error}` )
        // debugger
        // console.error('Dang ky khong thanh cong', error);
      }
    })
    //const headers = new HttpHeaders({ 'Content-Type': 'application/json', });
    //this.http.post(apiUrl, registerData , {headers},).subscribe();

  }

  onDateOfBirthChange() {}

  checkPasswordsMatch() {
    if (this.password !== this.retypePassword) {
      this.registerForm.form.controls['retypePassword'].setErrors({
        passwordMismatch: true,
      });
    } else {
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }

  checkAge() {
    if (this.dateOfBirth) {
      const today = new Date();
      const birthDate = new Date(this.dateOfBirth);
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();
      if (
        monthDiff < 0 ||
        (monthDiff === 0 && today.getDate() < birthDate.getDate())
      ) {
        age--;
      }
      if (age < 18) {
        this.registerForm.form.controls['dateOfBirth'].setErrors({
          invalidAge: true,
        });
      } else {
        this.registerForm.form.controls['dateOfBirth'].setErrors(null);
      }
    }
  }
}
