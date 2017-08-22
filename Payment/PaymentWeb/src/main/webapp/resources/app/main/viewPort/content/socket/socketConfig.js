
import sockJs from 'sockjs-client';
import stomp from 'stompjs';

"use strict";
{
    function socketService(utilsService, $rootScope) {

        const me = this;

        me.connect = function () {
            // close no delay notification, if any.
            utilsService.closeNoDelayNotification();
            var socket = new sockJs('openSocket', null, { transports: 'websocket' });
            var stompClient = stomp.over(socket);
            stompClient.debug = null; // To disable console statements
            stompClient.connect({},
                // Success callback
                function (frame) {
                    console.log('Connected: ' + frame);
                    stompClient.subscribe('/topic/updates', function (greeting) {
                        console.log('received update');
                        console.log(greeting);
                        console.log('received update body');
                        console.log(greeting.body);
                        $rootScope.$broadcast('socketUpdate', {
                            'class': greeting.headers.class, 'value': JSON.parse(greeting.body)
                        });
                    });
                },
                // errorCallback
                function (frame) {
                    onSocketConnectionError(frame);
                }

            ); // END  - stompClient.connect()

        };

        function onSocketConnectionError(frame) {
            utilsService.noDelayError('Socket connection Lost !!');
            console.log('socket disconnected, time is: ' + new Date());
            console.log(frame);
            me.connect();
        };

    };

    angular.module('payment').service('socketService', socketService);

    /* Initilize this service manually */
    angular.module('payment').run(['socketService', function (socketService) {
        socketService.connect();
    }]);

};