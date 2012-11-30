define([
    "jquery",
    "backbone"
], function ($, Backbone) {
    "use strict";

    var Collection = Backbone.Collection.extend({

        constructor : function () {
            Backbone.Collection.prototype.constructor.apply(this, arguments);

            // Create a deferred object that will be resolved when the collection is reset.
            this.resetDeferred = $.Deferred();
            this.on("reset", function () {
                this.resetDeferred.resolve(this);
            }, this);
        },

        promiseReset : function () {
            return this.resetDeferred.promise();
        },

        resetFromData : function (data) {
            if (data) {
                this.reset(data);
            }
        },

        removeFromData : function (data) {
            if (!this.model || !this.model.prototype || !this.model.prototype.idAttribute || !data) {
                return;
            }
            var that = this;
            this.promiseReset().done(function (collection) {
                var model = collection.get(data[that.model.prototype.idAttribute]);
                if (model) {
                    collection.remove(model);
                }
            });
        },

        updateFromData : function (data) {
            if (!this.model || !this.model.prototype || !this.model.prototype.idAttribute || !data) {
                return;
            }
            var that = this;
            this.promiseReset().done(function (collection) {
                var model = collection.get(data[that.model.prototype.idAttribute]);
                if (model) {
                    model.set(data);
                } else {
                    collection.add(data);
                }
            });
        }
    });

    return Collection;
});
