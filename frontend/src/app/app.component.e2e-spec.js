describe('App', function () {

  beforeEach(function () {
    browser.get('/');
  });

  it('should have a title', function () {
    expect(browser.getTitle()).toEqual("Angular 2 App | ng2-webpack");
  });

  it('should have <tov-nav>', function () {
    expect(element(by.css('my-app top-nav')).isPresent()).toBe(true);
  });

  it('should have <sidebar-menu>', function () {
    expect(element(by.css('my-app sidebar-menu')).isPresent()).toBe(true);
  });

  it('should have a main title', function () {
    expect(element(by.css('main div.title h2')).getText()).toEqual('APP');
  });

  it('should have a main div', function () {
    expect(element(by.css('my-app div#main')).isPresent()).toBe(true);
  });

});
