import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { LayoutModule } from '@angular/cdk/layout';
import { NgxsModule } from '@ngxs/store';
import { NgxsStoragePluginModule } from '@ngxs/storage-plugin';

import { MaterialModule } from './material/material.module';
import { CoreModule } from './core/core.module';

import { states } from './states';
import { environment } from '../environments/environment';
import { AuthModule } from './auth/auth.module';
import { AuthRoutingModule } from './auth/auth-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { EventsModule } from './events/events.module';
import { EventsRoutingModule } from './events/events-routing.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    MaterialModule,
    LayoutModule,
    CoreModule,
    AuthModule,
    AuthRoutingModule,
    EventsModule,
    EventsRoutingModule,
    HttpClientModule,
    NgxsModule.forRoot(states, {
      developmentMode: !environment.production
    }),
    NgxsStoragePluginModule.forRoot({
      key: ['auth.token', 'auth.currentUser']
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
