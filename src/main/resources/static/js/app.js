 // Angular entry point into the application
 var app = angular.module("myApp", ["ngRoute", "ngResource"]);

// defining routes
 app.config(function ($routeProvider) {
     $routeProvider
         .when('/', {
             templateUrl: '/template/home.html',
             controller: 'homeController'
         }).when('/list-all-users',{
             templateUrl: '/template/userlist.html',
             controller: 'listUserController'
         }).when('/register-new-user', {
             templateUrl: '/template/userregistration.html',
             controller: 'registerUserController'
         }).when('/update-user/:id',{
             templateUrl: '/template/updateuser.html',
             controller: 'usersDetailsController'
         }).when('/login', {
             templateUrl: '/login/login.html',
             controller: 'loginController'
         }).when('/logout', {
             templateUrl: '/login/login.html',
             controller: 'logoutController'
         }).otherwise({
             redirectTo: '/login'
     });
 });

 app.config(['$httpProvider', function ($httpProvider) {
     $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
 }]);
