define([
    "backbone",
    "factory/Collection",
    "factory/WebSocket"
], function (Backbone, Collection, webSocket) {
    "use strict";

    var Order = {};

    Order.Model = Backbone.Model.extend({
        idAttribute : "id",
        urlRoot : "/api/order"
    });

    Order.Collection = Collection.extend({
        model : Order.Model,
        url : "/api/order",

        initialize : function () {
            webSocket.on("order:list", this.resetFromData, this);
            webSocket.on("order:added", this.updateFromData, this);
            webSocket.on("order:updated", this.updateFromData, this);
            webSocket.on("order:deleted", this.removeFromData, this);

            webSocket.send("order:list");
        }
    });

    Order.orders = new Order.Collection();

    return Order;
});
