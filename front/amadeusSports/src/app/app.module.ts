import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LogInComponent } from './log-in/log-in.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserRegisterComponent } from './user-register/user-register.component';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';
import { UserService } from './service/user.service';
import { HttpClientModule } from '@angular/common/http';
import { MatSidenavModule } from '@angular/material/sidenav';
import { SideBarComponent } from './side-bar/side-bar.component';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router';
@NgModule({
  declarations: [
    AppComponent,
    LogInComponent,
    UserRegisterComponent,
    SideBarComponent,
  ],
  imports: [
    RouterModule.forRoot([
      { path: 'log-in', component: LogInComponent },
      { path: 'register', component: UserRegisterComponent },
    ]),
    MatFormFieldModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    FormsModule,
    ReactiveFormsModule,
    RxReactiveFormsModule,
    HttpClientModule,
    MatSidenavModule,
    MatButtonModule,

  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
