import { Route, RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "./components/home/home.component";
import { LoginComponent } from "./components/login/login.component";
import { RegisterComponent } from "./components/register/register.component";
import { DetailProductComponent } from "./components/detail-product/detail-product.component";
import { OrderComponent } from "./components/order/order.component";
import { NgModule } from "@angular/core";
import { OrderDetailComponent } from "./components/detail-order/order.detail.component";
import { UserProfileComponent } from "./components/user-profile/user.profile.component";
import { AuthGuardFn } from "./guards/auth.guard";
import { AdminGuard, AdminGuardFn } from "./guards/admin.guard";
import { AdminComponent } from "./components/admin/admin.component";

const routes: Routes = [
    { path:'', component: HomeComponent},
    { path:'login',component: LoginComponent},
    { path:'admin',component:AdminComponent, canActivate:[AdminGuardFn]},
    { path:'register', component: RegisterComponent},
    { path:'products/:id', component: DetailProductComponent},
    { path:'orders', component: OrderComponent, canActivate:[AuthGuardFn] },
    { path: 'user-profile', component: UserProfileComponent, canActivate:[AuthGuardFn] },
    { path: 'orders/:id', component: OrderDetailComponent },
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {}