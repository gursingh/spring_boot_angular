var app = angular.module('demo', [ 'ui.bootstrap',
		'ngRoute', 'angularFileUpload', ]);

app.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'template/upload_file.html',
		controller : 'uploaderCtrl'
	}).when('/confirmationinfo', {
		templateUrl : 'template/user_confirmation_form.html',
		controller : 'confirmationFormCtrl'
	}).when('/emailstatus', {
		templateUrl : 'template/emailstatus.html',
		controller : 'emailStatusCtrl'
	}).when('/notfound', {
		templateUrl : 'template/notfound.html',
		controller : 'notFoundCtrl'
	}).otherwise({
		redirectTo : '/notfound'
	});
});