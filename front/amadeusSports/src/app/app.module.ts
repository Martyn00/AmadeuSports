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
import { MatTableModule } from '@angular/material/table';
import { MatchTabsComponent } from './match-tabs/match-tabs.component';
import { MatTabsModule } from '@angular/material/tabs';
import { MatchesTableComponent } from './matches-table/matches-table.component';
import { MatchTableLoaderService } from './service/match-table-loader.service';
import { UpperBarComponent } from './upper-bar/upper-bar.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { PrincipalComponentLoaderService } from './service/principal-component-loader.service';
@NgModule({
  declarations: [
    AppComponent,
    LogInComponent,
    UserRegisterComponent,
    SideBarComponent,
    MatchTabsComponent,
    MatchesTableComponent,
    UpperBarComponent
  ],
  imports: [
    RouterModule.forRoot([
      { path: 'log-in', component: LogInComponent },
      { path: 'register', component: UserRegisterComponent },
      // { path: 'matches-table', component: MatchesTableComponent },
      { path: 'match-tabs', component: MatchTabsComponent }

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
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule
    
  ],
  providers: [UserService, MatchTableLoaderService, PrincipalComponentLoaderService],
  bootstrap: [AppComponent]
})
export class AppModule { }
