import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  private KEY_NAME = 'token';
  public get(key = this.KEY_NAME): any {
    return localStorage.getItem(key);
  }

  public set(value: string, key = this.KEY_NAME): void {
    localStorage.setItem(key, value);
  }

  public setToken(email: string, password: string) {
    this.set(this.encodeBasicAuthCredentials(email, password));
  }

  public remove(): void {
    localStorage.removeItem(this.KEY_NAME);
  }

  private encodeBasicAuthCredentials(email: string, password: string) {
    return window.btoa(email + ':' + password);
  }
}
