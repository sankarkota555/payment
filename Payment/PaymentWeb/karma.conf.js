module.exports = function(config) {
	config.set({
		frameworks : [ 'browserify', 'jasmine' ],
		files : [
				__dirname + '/node_modules/jquery/dist/jquery.js',
				__dirname + '/node_modules/angular/angular.js',
				__dirname
						+ '/node_modules/angular-mocks/angular-mocks.js',
				__dirname
						+ '/node_modules/angular-sanitize/angular-sanitize.js',
				__dirname
						+ '/node_modules/angular-animate/angular-animate.js',
				__dirname
						+ '/node_modules/angular-xeditable/dist/js/xeditable.js',
				__dirname
						+ '/node_modules/angular-local-storage/dist/angular-local-storage.js',
				__dirname
						+ '/node_modules/angular-ui-bootstrap/dist/ui-bootstrap.js',
				__dirname
						+ '/node_modules/angular-ui-router/release/angular-ui-router.js',
				__dirname + '/node_modules/ng-flow/dist/ng-flow.js',
				__dirname
						+ '/node_modules/angular-ui-sortable/dist/sortable.js',
				__dirname
						+ '/node_modules/angular-drag-and-drop-lists/angular-drag-and-drop-lists.js',
				__dirname + '/src/main/webapp/resources/app/main/common/ng-tags-input.js',
				
				__dirname + '/src/main/webapp/resources/app/app.js',
		
				__dirname + "/src/main/webapp/resources/app/main/left-panel/tweet-store.services.js",
				__dirname + "/src/main/webapp/resources/app/main/facebook/left-panel/post-store.services.js",
				__dirname + '/src/test/javascript/**/*.js',
				__dirname + '/src/test/javascript/**/*.ts',
				__dirname + '/src/main/webapp/resources/app/**/*.js',
				__dirname + '/src/main/webapp/resources/app/**/*.ts',
				__dirname + '/src/test/webapp/resources/app/**/*.js' ],
		exclude : [
				'src/main/webapp/resources/app/main/common/sessionExpiration.js',
				'src/main/webapp/resources/app/routes/routes.js',
				'src/main/webapp/resources/app/main/common/jquery.*.js',
				'src/main/webapp/resources/app/main/websocket.config.js',
				'src/main/javascript/application.ts' ],
		reporters : [ 'progress', 'junit' ],
		
		preprocessors : {
			'src/**/*.ts' : [ 'browserify' ],
			'src/**/*.js' : [ 'browserify' ]
		},
		
		browserify: {
			debug: true,
			transform: [
			            ['babelify', {presets: ['es2015']}],
			            'lessify'
			],
			
			plugin: [
			         ['tsify', {noResolve: false}]],
			extensions: ['.js', '.jsx', '.ts', '.css', '.less'],
			paths:['src/main/webapp/resources/app/', 'node_modules']
		},
		
		
		port : 9876,
		logLevel : config.LOG_INFO,
		browsers : [ 'PhantomJS' ],
		singleRun : false,
		autoWatch : true,
		// the default configuration
		junitReporter : {
			outputDir : './target/jasmine' // results will be saved as
		// $outputDir/$browserName.xml
		},
		plugins : [ 'karma-jasmine', 
		            'karma-phantomjs-launcher',
					'karma-chrome-launcher',
					'karma-browserify',
					'karma-junit-reporter'
						]
		});
};
