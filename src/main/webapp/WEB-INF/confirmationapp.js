var app = angular.module('confirmationapp', ['ngRoute','ui.bootstrap']);

app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'confirmation_template/user_confirmation_form.html',
            controller: 'confirmationFormCtrl'
        }).
        when('/success', {
            templateUrl: 'confirmation_template/successstatus.html',
            controller: 'successStatusCtrl'
        })
        .when('/notfound', {
            templateUrl: 'confirmation_template/notfound.html',
            controller: 'notFoundCtrl'
        }).otherwise({
        redirectTo: '/notfound'
    });
});