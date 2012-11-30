define([
    "backbone",
    "knockout",
    "factory/WebSocket",
    "modules/scale/ScaleController",
    "modules/order/OrderController"
], function (Backbone, Knockout, webSocket, ScaleController) {
    "use strict";

    var Factory = {};

    Factory.start = function () {
        webSocket.connect();
        ScaleController.listScales();
        Backbone.history.start();
    };

    // Add a custom binding to Knockout to allow us to stop bindings to be applied. This
    // allow us to nest views with different bindings without overlapping view models.
    Knockout.bindingHandlers.stopBindings = {
        init : function () {
            return { "controlsDescendantBindings" : true };
        }
    };
    Knockout.virtualElements.allowedBindings.stopBindings = true;

    return Factory;
});
