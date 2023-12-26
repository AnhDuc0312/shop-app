import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { OrderComponent } from './components/order/order.component';
import { OrderConfirmComponent } from './components/order-confirm/order-confirm.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { DetailProductComponent } from './components/detail-product/detail-product.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// import { HttpClientModule } from '@angular/common/http'
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { AppComponent } from './app/app.component';
import { AppRoutingModule } from './app-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserProfileComponent } from './components/user-profile/user.profile.component';
import { OrderDetailComponent } from './components/detail-order/order.detail.component';
import { CommonModule } from '@angular/common';
import { AdminModule } from './components/admin/admin.module';
import { AdminComponent } from './components/admin/admin.component';
import { AdminRoutingModule } from './components/admin/admin-routing.module';



@NgModule({
  declarations: [
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    OrderComponent,
    OrderConfirmComponent,
    LoginComponent,
    RegisterComponent,
    OrderDetailComponent, 
    DetailProductComponent,
    UserProfileComponent,
    AppComponent,
    
  ],
  imports: [
  
    CommonModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgbModule,
    AdminRoutingModule
  
    
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }],
  bootstrap: [
    AppComponent
    
  ]
})
export class AppModule { }
