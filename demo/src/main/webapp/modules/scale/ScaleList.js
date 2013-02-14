define([
    "backbone",
    "knockback",
    "knockout",
    "factory/WebSocket",
    "modules/scale/Scale",
    "text!modules/scale/ScaleList.html"
], function (Backbone, Knockback, Knockout, webSocket, Scale, scaleListTemplate) {
    "use strict";

    var ScaleList = Backbone.View.extend({

        render : function () {
            this.$el.html(scaleListTemplate);

            Knockout.applyBindings({

                // Create the view model for the collection.
                collection : Knockback.collectionObservable(this.collection, {
                    view_model : function (model) {

                        // Create the view model for each model.
                        var viewModel = new Knockback.ViewModel(model);

                        // Attach computed properties to the view model.
                        viewModel.progress = Knockout.computed(function () {
                            return this.target() === 0 ? "0%" : this.current() / this.target() * 100 + "%";
                        }, viewModel);

                        return viewModel;
                    }
                }),

                hidden : function () {
                    if (this.collection() && this.collection().length > 0) {
                        return this.collection()[0].hidden();
                    } else {
                        return false;
                    }
                },

                // Attach event handlers to the view model.
                toggleActive : function () {
                    if (this.active()) {
                        webSocket.send("scale:inactivate", { name : this.name() });
                    } else {
                        webSocket.send("scale:activate", { name : this.name() });
                    }
                }
            }, this.el);
        }
    });

    return ScaleList;
});
