import { IUser } from '@core/models';

export interface UsersStateModel {
  users: IUser[],
  blockedUsers: IUser[]
}
