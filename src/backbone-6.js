Order.Collection = Backbone.Collection.extend({
    model : Order.Model,
    url : "/api/order",

    initialize : function () {
        webSocket.on("order:list", this.resetFromData, this);
        webSocket.on("order:added", this.updateFromData, this);
        webSocket.on("order:updated", this.updateFromData, this);

        webSocket.send("order:list");
    },
