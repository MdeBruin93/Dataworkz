import { SetTitle } from './app.actions';

describe('appAction', () => {
  let component: SetTitle;

  beforeEach(() => {
    component = new SetTitle('string');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});