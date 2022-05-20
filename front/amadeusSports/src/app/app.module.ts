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
import { FavoritesComponent } from './favorites/favorites.component';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { UserEmulationInterceptor } from './interceptor/UserEmulationInterceptor';
import { BetService } from './service/bet.service';
import { BetsComponent } from './bet/bets/bets.component';
import { FriendsComponent } from './friends/friends.component';
import { MatDividerModule } from '@angular/material/divider';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FriendService } from './service/friend.service';
import { BetTableComponent } from './bet/bet-table/bet-table.component';
import { BetHistoryTableComponent } from './bet/bet-history-table/bet-history-table.component';
import { UpcomingBetTableComponent } from './bet/upcoming-bet-table/upcoming-bet-table.component';
@NgModule({
  declarations: [
    AppComponent,
    LogInComponent,
    UserRegisterComponent,
    SideBarComponent,
    MatchTabsComponent,
    MatchesTableComponent,
    UpperBarComponent,
    FavoritesComponent,
    BetsComponent,
    FriendsComponent,
    BetTableComponent,
    BetHistoryTableComponent,
    UpcomingBetTableComponent
  ],
  imports: [
    RouterModule.forRoot([
      { path: 'log-in', component: LogInComponent },
      { path: 'register', component: UserRegisterComponent },
      {
        path: 'bets', component: BetsComponent
      },
      { path: 'match-tabs', component: MatchTabsComponent },
      { path: 'friends', component: FriendsComponent },
      { path: 'favorites', component: FavoritesComponent },
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
    MatMenuModule,
    MatSelectModule,
    MatButtonToggleModule,
    MatDividerModule,
    MatAutocompleteModule,
    MatSnackBarModule
  ],
  providers: [UserService, MatchTableLoaderService, PrincipalComponentLoaderService, BetService, UserEmulationInterceptor, FriendService],
  bootstrap: [AppComponent]
})
export class AppModule { }
