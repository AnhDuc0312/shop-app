import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import { NgForm } from '@angular/forms';
import { LoginDTO } from '../../dtos/user/login.dto';
import { LoginResponse } from '../../responses/user/login.response';
import { TokenService } from 'src/app/service/token.service';
import { RoleService } from 'src/app/service/role.service';
import { Role } from 'src/app/models/role';
import { UserResponse } from 'src/app/responses/user/user.response';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string = '';
  password: string = '';
  showPassword: boolean = false;

  roles: Role[] = [];
  rememberMe: boolean = true;
  selectedRole: Role | undefined;
  userResponse?: UserResponse;

  constructor( 
    private router: Router, 
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
    
    ) {
    
  }

  onPhoneNumberChange() {
    console.log(`Phone type : ${this.phoneNumber}`);
  }
  ngOnInit() {
    debugger
    this.roleService.getRoles().subscribe({
      next: (roles: Role[]) => {
        debugger
        this.roles = roles;
        this.selectedRole = roles.length > 0 ? roles[0] : undefined;
      },
      error: (error: any) =>{
        debugger
        console.error("Error getting roles:", error);
      }
    });
  }

  login() {
    const message = `phone: ${this.phoneNumber}` +
      `password: ${this.password}`;
    //alert(message);
    debugger

    const loginDTO: LoginDTO = {
      phone_number: this.phoneNumber,
      password: this.password,
      role_id: this.selectedRole?.id ?? 1
    };
    this.userService.login(loginDTO).subscribe({
      next: (response: LoginResponse) => {
        debugger;
        const { token } = response;
        if (this.rememberMe) {          
          this.tokenService.setToken(token);
          debugger;
          this.userService.getUserDetail(token).subscribe({
            next: (response: any) => {
              debugger
              this.userResponse = {
                ...response,
                date_of_birth: new Date(response.date_of_birth),
              };    
              this.userService.saveUserResponseToLocalStorage(this.userResponse); 
              if(this.userResponse?.role.name == 'admin') {
                this.router.navigate(['/admin']);    
              } else if(this.userResponse?.role.name == 'user') {
                this.router.navigate(['/']);                      
              }
              
            },
            complete: () => {
              debugger;
            },
            error: (error: any) => {
              debugger;
              alert(error.error.message);
            }
          })
        }                        
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        alert(error.error.message);
      }
    });
  }
  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  createAccount() {
    debugger
    // Chuyển hướng người dùng đến trang đăng ký (hoặc trang tạo tài khoản)
    this.router.navigate(['/register']); 
  }
}
