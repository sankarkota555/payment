module.exports = function (config) {

    const nodeModulePrefixLocation = __dirname + '/node_modules/';
    const resMainPrefix = __dirname + '/src/main/webapp/resources/app/main/';
    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        //basePath: '',


        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine', 'browserify'],


        // list of files / patterns to load in the browser
        files: [
            nodeModulePrefixLocation + 'angular/angular.min.js',
            nodeModulePrefixLocation + 'angular-mocks/angular-mocks.js',
            nodeModulePrefixLocation + 'angular-ui-router/release/angular-ui-router.min.js',
            nodeModulePrefixLocation + 'angular-material/angular-material.js',
            nodeModulePrefixLocation + 'jquery/dist/jquery.min.js',
            resMainPrefix + 'initModule.js',
            resMainPrefix + 'appDependencies.js',
            resMainPrefix + '**/!content.template.html',
            resMainPrefix + 'viewPort/**/*.js',
            //__dirname + '/src/main/webapp/resources/app/test/**/*.js',

        ],


        /*********************************************************/
        // Note: this was added AFTER karma init was completed.
        /*********************************************************/
        /* ngHtml2JsPreprocessor: {
             // strip this from the file path
            // stripPrefix: 'src/',
             // prepend this to the
 
 
             // setting this option will create only a single module that contains templates
             // from all the files, so you can load them all with module('foo')
             moduleName: 'templatesForTest'
         }, */

        browserify: {
            debug: true,
            transform: [
                ['babelify', { presets: ['es2015'] }],
                'lessify'
            ],

            plugin: [
                ['tsify', { noResolve: false }]],
            extensions: ['.js', '.jsx', '.ts', '.css', '.less'],
            paths: ['src/main/webapp/resources/app/', 'node_modules'],
            exclude: [
                "src/main/webapp/resources/app/main/viewPort/view-port.template.html"
            ]
        },

        // list of files to exclude
        //exclude: [__dirname + '/src/main/webapp/resources/app/main/viewPort/view-port.template.html'],


        // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {
            'src/**/*.ts': ['browserify'],
            'src/**/*.js': ['browserify'],
            //'**/*.html': ['ng-html2js'],
        },


        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['progress'],


        // web server port
        port: 9876,


        // enable / disable colors in the output (reporters and logs)
        colors: true,


        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,


        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,


        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['Chrome'],


        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: false,

        plugins: ['karma-jasmine',
            'karma-phantomjs-launcher',
            'karma-chrome-launcher',
            'karma-browserify',
            'karma-junit-reporter',
            //'karma-ng-html2js-preprocessor',
        ]
    });
};
