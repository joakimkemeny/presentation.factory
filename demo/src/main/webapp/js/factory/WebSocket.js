define([
    "jquery",
    "underscore",
    "backbone",
    "factory/Object"
], function ($, _, Backbone, Object) {
    "use strict";

    var WebSocket = Object.extend({

        constructor : function (protocol) {
            this.protocol = protocol;
            this.connectDeferred = $.Deferred();
        },

        connect : function () {
            var ws;
            var url = "ws://" + location.host + "/ws";

            if (window.WebSocket) {
                ws = new window.WebSocket(url, this.protocol);
            } else {
                throw new Error("WebSockets is not supported");
            }

            var that = this;

            $(ws).on("open", function () {
                that.connectDeferred.resolve(that);
            });
            $(ws).on("close", function () {
                that.connectDeferred = $.Deferred();
            });
            $(ws).on("message", function (e) {
                var message = JSON.parse(e.originalEvent.data);
                that.trigger(message.protocol + ":" + message.command, message.data);
            });

            this.ws = ws;

            $(window).on("unload", function () {
                ws.close();
                ws = null;
            });
        },

        send : function (command, variables, data) {

            var that = this;
            this.connectDeferred.done(function () {
                that.ws.send(JSON.stringify({
                    protocol : command.substring(0, command.indexOf(":")),
                    command : command.substring(command.indexOf(":") + 1),
                    variables : variables,
                    data : data
                }));
            });
        }
    });

    _.extend(WebSocket.prototype, Backbone.Events);

    return new WebSocket("factory");
});
