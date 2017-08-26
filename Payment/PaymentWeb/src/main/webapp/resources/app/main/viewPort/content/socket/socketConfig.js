
import sockJs from 'sockjs-client';
import stomp from 'stompjs';

"use strict";
{
    function socketService(utilsService, $rootScope) {

        const me = this;

        let socketRetryCount = 0;

        me.connect = function () {
            var socket = new sockJs('openSocket', null, { transports: 'websocket' });
            var stompClient = stomp.over(socket);
            stompClient.debug = null; // To disable console statements
            stompClient.connect({},
                // Success callback
                function (frame) {
                    console.log('Connected: ' + frame);
                    // Reset socketRetryCount
                    socketRetryCount = 0;

                    stompClient.subscribe('/topic/updates', function (greeting) {
                        console.log('received update');
                        console.log(greeting);
                        console.log('received update body');
                        console.log(greeting.body);
                        $rootScope.$broadcast('socketUpdate', {
                            'class': greeting.headers.class, 'value': JSON.parse(greeting.body)
                        });

                        $rootScope.$apply();
                    });
                },
                // errorCallback
                function (frame) {
                    onSocketConnectionError(frame);
                }

            ); // END  - stompClient.connect()

        };

        function onSocketConnectionError(frame) {
            if (socketRetryCount >= 3) {
                utilsService.noDelayError('Socket connection Lost !!');
            } else {
                socketRetryCount++;
                console.log("Auto reconnecting to socket. count: " + socketRetryCount);
                // Retry for connection after some time delay
                setTimeout(function () {
                    me.connect();
                }, socketRetryCount * 5000);

            }

            console.log('socket disconnected, time is: ' + new Date());

        };

    };

    angular.module('payment').service('socketService', socketService);

    /* Initilize this service manually */
    angular.module('payment').run(['socketService', function (socketService) {
        socketService.connect();
    }]);

};