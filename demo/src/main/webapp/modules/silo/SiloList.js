define([
    "backbone",
    "knockback",
    "knockout",
    "modules/silo/Silo",
    "text!modules/silo/SiloList.html"
], function (Backbone, Knockback, Knockout, Silo, siloListTemplate) {
    "use strict";

    var SiloList = Backbone.View.extend({

        render : function () {
            this.$el.html(siloListTemplate);

            var scaleName = this.options.scale;

            Knockout.applyBindings({

                // Create the view model for the collection.
                collection : Knockback.collectionObservable(this.collection, {
                    view_model : function (model) {

                        // Create the view model for each model.
                        var viewModel = new Knockback.ViewModel(model);

                        // Attach computed properties to the view model.
                        viewModel.progress = Knockout.computed(function () {
                            return this.capacity() === 0 ? "0%" : this.level() / this.capacity() * 100 + "%";
                        }, viewModel);

                        return viewModel;
                    },

                    // Add filter to exclude silos for other scales.
                    filters : [
                        function (model) {
                            return model.get("scale") !== scaleName;
                        }
                    ]
                })
            }, this.el);
        }
    });

    return SiloList;
});
