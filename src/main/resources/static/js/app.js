 // Angular entry point into the application
 var app = angular.module("myApp", ["ngRoute", "ngResource"]);

// defining routes
 app.config(function ($routeProvider) {
     $routeProvider
         .when('/list-all-users',{
             templateUrl: '/template/userlist.html',
             controller: 'listUserController'
         }).when('/register-new-user', {
             templateUrl: '/template/userregistration.html',
             controller: 'registerUserController'
         }).when('/update-user/:id',{
             templateUrl: '/template/updateuser.html',
             controller: 'usersDetailsController'
         }).otherwise({
             redirectTo: '/home',
             templateUrl: '/template/home.html'
     });
 });
