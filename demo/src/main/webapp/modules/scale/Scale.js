define([
    "backbone",
    "factory/Collection",
    "factory/WebSocket"
], function (Backbone, Collection, webSocket) {
    "use strict";

    var Scale = {};

    Scale.Model = Backbone.Model.extend({
        idAttribute : "name"
    });

    Scale.Collection = Collection.extend({
        model : Scale.Model,

        initialize : function () {
            webSocket.on("scale:list", this.resetFromData, this);
            webSocket.on("scale:updated", this.updateFromData, this);

            webSocket.send("scale:list");
        }
    });

    Scale.scales = new Scale.Collection();

    return Scale;
});
