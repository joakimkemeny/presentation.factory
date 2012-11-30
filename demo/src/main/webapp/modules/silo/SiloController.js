define([
    "jquery",
    "modules/silo/Silo",
    "modules/silo/SiloList"
], function ($, Silo, SiloList) {
    "use strict";

    var SiloController = {};

    SiloController.listSilosByScale = function (scale) {

        var siloList = new SiloList({
            collection : Silo.silos,
            el : $("#silo-list-" + scale + " .inner"),
            scale : scale
        });
        siloList.render();
    };

    return SiloController;
});
