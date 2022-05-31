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
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
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
import { FavoritesComponent } from './favoriteComponent/favorites/favorites.component';
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
import { FavoriteMatchesComponent } from './favoriteComponent/favorite-matches/favorite-matches.component';
import { FavoriteTeamsComponent } from './favoriteComponent/favorite-teams/favorite-teams.component';
import { FavoriteLeaguesComponent } from './favoriteComponent/favorite-leagues/favorite-leagues.component';
import { MatExpansionModule } from '@angular/material/expansion';
import { FavoritesTableComponent } from './favoriteComponent/favorites-table/favorites-table.component';
import { BetDialogComponent } from './bet/bet-dialog/bet-dialog.component';
import { MatDialogModule, MAT_DIALOG_DEFAULT_OPTIONS } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { AcceptBetDialogComponent } from './bet/accept-bet-dialog/accept-bet-dialog.component';


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
    UpcomingBetTableComponent,
    FavoriteMatchesComponent,
    FavoriteTeamsComponent,
    FavoriteLeaguesComponent,
    FavoritesTableComponent,
    BetDialogComponent,
    AcceptBetDialogComponent,
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
      {
        path: 'favorites', component: FavoritesComponent, children: [
          {
            path: 'matches', component: FavoriteMatchesComponent
          },
          {
            path: 'teams', component: FavoriteTeamsComponent
          },
          {
            path: 'leagues', component: FavoriteLeaguesComponent
          }
        ]
      },
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
    MatSnackBarModule,
    MatExpansionModule,
    MatDialogModule,
    MatCheckboxModule,
  ],
  providers: [UserService, MatchTableLoaderService, PrincipalComponentLoaderService, BetService, FriendService, {
    provide: HTTP_INTERCEPTORS,
    useClass: UserEmulationInterceptor,
    multi: true
  },
    { provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: { hasBackdrop: false } }],
  bootstrap: [AppComponent]
})
export class AppModule { }
