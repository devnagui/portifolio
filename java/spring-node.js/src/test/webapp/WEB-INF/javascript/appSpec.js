describe('The sayHello function', function(){
  it('should greet with name', function(){
    expect(sayHello("World")).toBe("Hello World!");
  });
});