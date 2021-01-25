import { CategoriesService } from '@categories/services';
import { AppState } from './app.state';

describe('appState', () => {
  let appState: AppState;

  beforeEach(() => {
    appState = new AppState();
  });

  it('should create', () => {
    expect(appState).toBeTruthy();
  });
});