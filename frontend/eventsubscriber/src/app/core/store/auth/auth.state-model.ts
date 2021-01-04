import { IUser } from '@core/models';

export interface AuthStateModel {
  currentUser: any,
  token: string
}