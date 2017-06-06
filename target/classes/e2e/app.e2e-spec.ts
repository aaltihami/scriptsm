import { TsmPage } from './app.po';

describe('tsm App', () => {
  let page: TsmPage;

  beforeEach(() => {
    page = new TsmPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
