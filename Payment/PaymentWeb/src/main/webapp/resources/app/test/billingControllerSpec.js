"use strict";
describe('Billing Controller', function () {

    let $scope, ctrl = null, $q, rootScope, $compile, httpBackend;
    let deferred;

	/*
	 * declare our mocks out here so we can use them through the scope of this
	 * describe block.
	 */
    let itemsServiceMock, utilsServiceMock,billingServiceMock;

    // This function will be called before every "it" block.
    // This should be used to "reset" state for your tests.
    beforeEach(function () {
        // Create a "spy object" for our Service.
        // This will isolate the controller we're testing from
        // any other code.
        // we'll set up the returns for this later
        billingServiceMock = jasmine.createSpyObj('billingService', []);
        utilsServiceMock = jasmine.createSpyObj('utilsServiceMock', []);
        itemsServiceMock = jasmine.createSpyObj('itemsServiceMock', []);
        // load the module you're testing.
        angular.mock.module('payment');

        // INJECT! This part is critical
        // $rootScope - injected to create a new $scope instance.
        // $controller - injected to create an instance of our controller.
        // $q - injected so we can create promises for our mocks.
        inject(function ($rootScope, $controller, _$q_, _$compile_, $httpBackend) {
            // create a scope object for us to use.
            rootScope = $rootScope;
            $scope = $rootScope.$new();
            $q = _$q_;
            $compile = _$compile_;

            httpBackend = $httpBackend;
            // set up the returns for our someServiceMock
            // $q.defer() promise.
            // this is important since our service is async and returns
            // a promise.
            deferred = $q.defer();
            //aaUsersSvcMock.getAll.and.returnValue(deferred.promise);
           // aaUsersSvcMock.offline.and.returnValue(deferred.promise);
            //assignmentSvcMock.assignTweets.and.returnValue(deferred.promise);

            spyOn($scope, '$on').and.callThrough();


            // now run that scope through the controller function,
            // injecting any services or other injectables we need.
            // **NOTE**: this is the only time the controller function
            // will be run, so anything that occurs inside of that
            // will already be done before the first spec.
            ctrl = $controller('aaUserController', {
                $scope: $scope,
                billingService: billingServiceMock,
                itemsService: itemsServiceMock,
                utilsService: utilsServiceMock,
            });
        });
    });


	/*
	 * Test 1: Testing to get agents option of aaUser controller.
	 * This test, tests for success.
	 */
    it('should return agents when getAll() is called', function () {
        let data = {
            "_links": {
                "search": {
                    "href": "http://localhost:8080/Snap/svc/aAUsers/search"
                }
            },
            "_embedded": {

                "aAUsers": [{
                    "employeeId": 928944,
                    "role": "Administrator",
                    "displayedName": "Narvar",
                    "firstName": "Tony",
                    "lastName": "Narvarte",
                    "email": "tnarvarte@gmail.com",
                    "online": false,
                    "lastActivity": null,
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/Snap/svc/aAUsers/928944"
                        }
                    }
                }, {
                    "employeeId": 991477,
                    "role": "Administrator",
                    "displayedName": "SathyaKanuri",
                    "firstName": "Sathya",
                    "lastName": "Kanuri",
                    "email": "satya.kanuri@zyksa.com",
                    "online": true,
                    "lastActivity": 1405490889472,
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/Snap/svc/aAUsers/991477"
                        }
                    }
                }]
            }
        };

        let response = {
            status: 200,
            data: data
        };

        //$scope.getAll();

        deferred.resolve(response);
        $scope.$apply();

        expect(aaUsersSvcMock.getAll).toHaveBeenCalled();
        expect(data).not.toEqual(null);
    });

	/*
	 * Test 2: Testing to get agents option of aaUser controller.
	 * This test, tests for failure.
	 */
    it('should produce no agents but a 500 error', function () {

        // set up.

        let response = {
            status: 500
        };

        // make the call.
        //$scope.getAll();
        deferred.reject(response);

        $scope.$apply();

        //expected calls
        expect(aaUsersSvcMock.getAll).toHaveBeenCalled();
        expect(utilSvcMock.processError).toHaveBeenCalled();
    });


    it('simulate broadcasting agent', function () {

        ctrl.aaUsers = [{
            "employeeId": 928944,
            "role": "Administrator",
            "displayedName": "Narvar",
            "firstName": "Tony",
            "lastName": "Narvarte",
            "email": "tnarvarte@gmail.com",
            "online": false,
            "lastActivity": null,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/Snap/svc/aAUsers/928944"
                }
            }
        }, {
            "employeeId": 991477,
            "role": "Administrator",
            "displayedName": "SathyaKanuri",
            "firstName": "Sathya",
            "lastName": "Kanuri",
            "email": "satya.kanuri@zyksa.com",
            "online": false,
            "lastActivity": 1405490889472,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/Snap/svc/aAUsers/991477"
                }
            }
        }];
        let agent = {
            'employeeId': 991477,
            'online': 'true',
            'lastActivity': 12345647
        };

        rootScope.$broadcast('agent', agent);
        $scope.$apply();

        expect(ctrl.aaUsers[1].online).toEqual("true");
        expect(ctrl.aaUsers[1].lastActivity).toEqual(12345647);
    });

    it('simulating marking an user status as away', function () {

        ctrl.aaUsers = [{
            "employeeId": 928944,
            "role": "Administrator",
            "displayedName": "Narvar",
            "firstName": "Tony",
            "lastName": "Narvarte",
            "email": "tnarvarte@gmail.com",
            "online": false,
            "lastActivity": null,
            "awayTimeout": 123,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/Snap/svc/aAUsers/928944"
                }
            }
        }, {
            "employeeId": 991477,
            "role": "Administrator",
            "displayedName": "SathyaKanuri",
            "firstName": "Sathya",
            "lastName": "Kanuri",
            "email": "satya.kanuri@zyksa.com",
            "online": false,
            "lastActivity": 1405490889472,
            "awayTimeout": null,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/Snap/svc/aAUsers/991477"
                }
            }
        }];


        ctrl.markAsAway();
        $scope.$apply();

        expect(ctrl.aaUsers[0].away).toEqual(true);
    });

    it('simulating marking an user status as offline', function () {

        ctrl.aaUsers = [{
            "employeeId": 928944,
            "role": "Administrator",
            "displayedName": "Narvar",
            "firstName": "Tony",
            "lastName": "Narvarte",
            "email": "tnarvarte@gmail.com",
            "online": false,
            "lastActivity": null,
            "offlineTimeout": 123,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/Snap/svc/aAUsers/928944"
                }
            }
        }, {
            "employeeId": 991477,
            "role": "Administrator",
            "displayedName": "SathyaKanuri",
            "firstName": "Sathya",
            "lastName": "Kanuri",
            "email": "satya.kanuri@zyksa.com",
            "online": false,
            "lastActivity": 1405490889472,
            "offlineTimeout": null,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/Snap/svc/aAUsers/991477"
                }
            }
        }];


        ctrl.markAsOffline();
        $scope.$apply();

        expect(ctrl.aaUsers[0].away).toEqual(false);
        expect(ctrl.aaUsers[0].online).toEqual(false);
    });

    it('should call drop listener if users group is dragged and dropped.', function () {

        let tweets = [{
            id: 123
        }, {
            id: 456
        }];

        let agent = {
            "employeeId": 928944,
            "role": "Administrator",
            "displayedName": "Narvar",
            "firstName": "Tony",
            "lastName": "Narvarte",
            "email": "tnarvarte@gmail.com",
            "online": false,
            "lastActivity": null,
            "_links": {
                "self": {
                    "href": "http://localhost:8080/Snap/svc/aAUsers/928944"
                }
            }
        };

        ctrl.selectionController = {
            'tweetIdsToArchive': [1, 2, 3]
        };

        ctrl.drop(agent, 'tweets', tweets);

        expect(assignmentSvcMock.assignTweets).toHaveBeenCalled();
    });

});