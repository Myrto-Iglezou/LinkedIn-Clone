import { RouterModule, Routes } from '@angular/router';
import { NgModule, Component } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';
import { UserService } from './services/user.service';
import {AuthenticationService} from './authentication.service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './auth/login/login.component';
import { FooterComponent } from './footer/footer.component';
import { MatSliderModule } from '@angular/material/slider';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ErrorInterceptor} from './auth/helpers/error.interceptor';
import { JwtInterceptor} from './auth/helpers/jwt.interceptor';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SignupComponent } from './auth/signup/signup.component';
import { AdminComponent } from './admin/admin.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { FeedComponent } from './feed/feed.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ProfileCardComponent } from './profile-card/profile-card.component';
import { CreatePostComponent } from './create-post/create-post.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { PostsinfeedComponent } from './postsinfeed/postsinfeed.component';
import {NgxPaginationModule} from 'ngx-pagination';
import { UsersettingsComponent } from './usersettings/usersettings.component';
import { NetworkComponent } from './network/network.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { JobsComponent } from './jobs/jobs.component';
import { AdminNavComponent } from './admin/admin-nav/admin-nav.component';
import { MessagingComponent } from './messaging/messaging.component';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  { path: 'users', children: [
    {path: ':id' , 
    children: [
        {path: '', component: UserDetailsComponent},
        // {path: 'passwordchange', component: ChangePasswordComponent, canActivate: [UserGuard]},
        // {path: 'edit', component: UserEditComponent, canActivate: [UserGuard]},
        // { path: 'messages', component: MessagePageComponent, canActivate: [UserGuard]}
        ] }
    ] 
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'signup',
    component: SignupComponent,
  },
  {
    path: 'feed',
    component: FeedComponent,
  },
  {
    path: 'messaging/:id',
    component: MessagingComponent,
  },
  {
    path: 'messaging',
    component: MessagingComponent,
  },
  { path: 'admin', children: [
    {path: '', component: AdminComponent},
    // {path: 'exportdata', component: AppDataExportComponent }
   ] //, canActivate: [AdminGuard]
  },
  { path: 'settings', component: UsersettingsComponent},
  { path: 'network', component: NetworkComponent},
  { path: 'notifications', component: NotificationsComponent},
  { path: 'jobs', component: JobsComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    FooterComponent,
    SignupComponent,
    AdminComponent,
    UserDetailsComponent,
    FeedComponent,
    NavbarComponent,
    ProfileCardComponent,
    CreatePostComponent,
    PostsinfeedComponent,
    UsersettingsComponent,
    NetworkComponent,
    NotificationsComponent,
    JobsComponent,
    AdminNavComponent,
    MessagingComponent,
  ],
  imports: [
    NgxPaginationModule,
    BrowserModule,
    MatSliderModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatGridListModule,
    MatRadioModule,
    RouterModule.forRoot(appRoutes),
    TooltipModule.forRoot(),
    NgbModule,
  ],
  providers:  [
    UserService,
    AuthenticationService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
