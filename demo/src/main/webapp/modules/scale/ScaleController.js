define([
    "jquery",
    "underscore",
    "modules/scale/Scale",
    "modules/scale/ScaleList",
    "modules/silo/SiloController"
], function ($, _, Scale, ScaleList, SiloController) {
    "use strict";

    var ScaleController = {};

    ScaleController.listScales = function () {

        var scaleList = new ScaleList({
            collection : Scale.scales,
            el : $("#scale-list")
        });
        scaleList.render();

        Scale.scales.promiseReset().done(function (scales) {
            scales.each(function (scale) {
                _.defer(function () {
                    SiloController.listSilosByScale(scale.id);
                });
            });
        });
    };

    return ScaleController;
});
