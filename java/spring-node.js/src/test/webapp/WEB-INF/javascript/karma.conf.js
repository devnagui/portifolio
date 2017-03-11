module.exports = function(config) {
  config.set({
    basePath: '../../../../..',
    frameworks: ['jasmine'],
    files: [
      '.bowercomponents/jquery/dist/jquery.js',
      'src/main/webapp/WEB-INF/javascript/*.js',
      'src/test/webapp/WEB-INF/javascript/*.js'
    ],
    exclude: ['src/test/webapp/WEB-INF/javascript/karma.conf*.js'],
    reporters: ['progress'],
    port: 9876,
    logLevel: config.LOG_INFO,
    browsers: ['PhantomJS'],
    singleRun: false,
    autoWatch: true,
    plugins: [
      'karma-jasmine',
      'karma-phantomjs-launcher'
    ]
  });
};