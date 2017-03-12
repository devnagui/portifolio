var pkgjson = require('./package.json');

var config = {
  src : 'src/main/webapp/WEB-INF',
  test : 'src/test/webapp/WEB-INF',
  dest : 'target/grunt/public',
  bower : '.bowercomponents',
};
config.sass = config.src + '/sass';
config.js = config.src + '/javascript';
config.js_test = config.test + '/javascript';

module.exports = function(grunt) {

  grunt.loadNpmTasks("grunt-bower-install-simple");
  grunt.loadNpmTasks('grunt-concurrent');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-karma');
  grunt.loadNpmTasks('grunt-sass');
  grunt.loadNpmTasks('grunt-angular-builder');
  grunt.loadNpmTasks('grunt-export');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt
      .initConfig({
        config : config,
        pkg : config.pkg,
        "bower-install-simple" : {
          options : {
            directory : config.bower,
          },
          dist : {
            options : {
              production : false,
            },
          },
        },
        concurrent : {
          target : {
            tasks : [ 'watch', 'karma' ],
            options : {
              logConcurrentOutput : true
            }
          }
        },
        karma : {
          dev: {
            configFile : '<%= config.js_test %>/karma.conf.js'
          }
        },
        sass : {
          dist : {
            options : {
              outputStyle : 'compressed',
              includePaths : [ '<%= config.bower %>/bootstrap-sass/assets/stylesheets/' ],
              sourceMap : true
            },
            files : {
              '<%= config.dest %>/styles.css' : '<%= config.sass %>/app.scss'
            }
          }
        },
        uglify : {
          dist : {
            options : {
              sourceMap : true,
            },
            files : {
            	  '<%= config.dest %>/scripts.js' : [
                  '<%= config.bower %>/modernizr/modernizr.js',
                  '<%= config.bower %>/jquery/dist/jquery.js',
                  '<%= config.bower %>/bootstrap-sass/assets/javascripts/bootstrap.js']
            }
          }
        },
        watch : {
          js : {
            files : [ '<%= config.js %>/**/*.js' ],
            tasks : [ 'uglify' ]
          },
          css : {
            files : [ '<%= config.sass %>/**/*.{scss,sass}' ],
            tasks : [ 'sass' ]
          }
        },
        exports: {
            test: {
              options: {
                verbose: true
              },
              files: {
                '<%= config.dest %>': ['<%= config.js  %>/angular.min.js','<%= config.js  %>/app.js'],
              }
            }
          },
        
        copy: {
        	dev: {
        	    files: [
        	      // includes files within path 
        	      { src: ['<%= config.bower %>/angular/angular.min.js'], dest: '<%= config.dest %>/angular.min.js'},
        	      // includes files within path and its sub-directories 
        	      { src: ['<%= config.js %>/angular.js'], dest: '<%= config.dest %>/angular.js'},
        	      { src: ['<%= config.js %>/app.js'], dest: '<%= config.dest %>/app.js'}
        	    ],
        	  }}
      });
  grunt.registerTask('release', ['angular-builder']);
  grunt.registerTask('js', [ 'uglify' ,'copy']);
  grunt.registerTask('css', [ 'sass' ]);
  grunt.registerTask('dist', [ 'bower-install-simple', 'js', 'css','copy']);
  grunt.registerTask('dev', [ 'concurrent' ]);
  grunt.registerTask('default', [ 'dev' ,'angular-builder','copy','copy:main']);
  grunt.registerTask('exports', [ 'test','files' ]);
};