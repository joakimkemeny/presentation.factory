require.config({

    baseUrl : "js",

    paths : {
        backbone : "libs/backbone/backbone",
        jquery : "libs/jquery/jquery",
        knockback : "libs/knockback/knockback",
        knockout : "libs/knockout/knockout",
        text : "libs/require/require.text",
        underscore : "libs/underscore/underscore",

        modules : "../modules"
    },

    shim : {
        backbone : {
            deps : ["underscore", "jquery"],
            exports : "Backbone"
        },
        bootstrap : ["jquery"],
        jquery : {
            exports : "$"
        },
        knockback : {
            deps : ["underscore", "backbone", "knockout"],
            exports : "kb"
        },
        underscore : {
            deps : ["jquery"],
            exports : "_"
        }
    }
});

require([
    "Factory"
], function (Factory) {
    Factory.start();
});
