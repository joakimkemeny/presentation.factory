define([
    "backbone",
    "factory/Collection",
    "factory/WebSocket"
], function (Backbone, Collection, webSocket) {
    "use strict";

    var Silo = {};

    Silo.Model = Backbone.Model.extend({
        idAttribute : "name"
    });

    Silo.Collection = Collection.extend({
        model : Silo.Model,

        initialize : function () {
            webSocket.on("silo:list", this.resetFromData, this);
            webSocket.on("silo:updated", this.updateFromData, this);

            webSocket.send("silo:list");
        }
    });

    Silo.silos = new Silo.Collection();

    return Silo;
});
