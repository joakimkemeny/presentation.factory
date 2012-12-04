// @codekit-prepend "libs/jquery.js"
// @codekit-prepend "libs/impress.js"
// @codekit-prepend "libs/impress.console.js"
// @codekit-prepend "libs/rainbow.js"
// @codekit-prepend "libs/language/c.js"
// @codekit-prepend "libs/language/csharp.js"
// @codekit-prepend "libs/language/css.js"
// @codekit-prepend "libs/language/generic.js"
// @codekit-prepend "libs/language/html.js"
// @codekit-prepend "libs/language/javascript.js"
// @codekit-prepend "libs/language/lua.js"
// @codekit-prepend "libs/language/php.js"
// @codekit-prepend "libs/language/python.js"
// @codekit-prepend "libs/language/ruby.js"
// @codekit-prepend "libs/language/scheme.js"
// @codekit-prepend "libs/language/shell.js"
// @codekit-prepend "smoke.js"

var $ = $ || {};
var WebSockets = WebSockets || {};

WebSockets = (function ($) {
    "use strict";
    var Presentation = {};

    Presentation.loadDemo = function (step) {
        if (step.data("demo-loaded")) {
            return;
        }

        var browser = step.find(".browser");
        var src = browser.data("src")
        browser.append("<iframe src='" + src + "' frameborder='0'></iframe>");

        step.data("demo-loaded", true);
    };

    Presentation.loadSrc = function (step) {
        if (step.data("src-loaded")) {
            return;
        }

        $("code[data-src]", step).each(function (index, element) {
            var $element = $(element)
            var srcUrl = $element.data("src");
            var srcLang = $element.data("language");
            $.get(srcUrl, function (data) {
                if (srcLang === "text" || srcLang === "") {
                    $element.append(data);
                } else {
                    Rainbow.color(data, srcLang, function (highlighted) {
                        $element.append(highlighted);
                    });
                }
            }, "text");
        });

        step.data("src-loaded", true);
    };

    Presentation.startSmoke = function (step) {
        initSmoke(step + "-smoke");
    };

    Presentation.stopSmoke = function () {
        setTimeout(function () {
            destroySmoke();
        }, 5000);
    };

    Presentation.addKeybindings = function () {
        $(document).keypress(function (e) {
            if (e.keyCode === 102) {
                document.getElementsByTagName("html")[0].webkitRequestFullScreen();
            }
        });
    };

    return Presentation;
}($));

$(function () {
    $(".step").on("impress:stepenter", function () {
        WebSockets.loadSrc($(this));
        WebSockets.loadDemo($(this));
    });
    $("#intro-1").on("impress:stepenter", function () {
        WebSockets.startSmoke("intro");
    });
    $("#intro-1").on("impress:stepleave", function () {
        WebSockets.stopSmoke("intro");
    });
    $("#outro-1").on("impress:stepenter", function () {
        WebSockets.startSmoke("outro");
    });
    $("#outro-1").on("impress:stepleave", function () {
        WebSockets.stopSmoke("outro");
    });

    WebSockets.addKeybindings();

    console().init();
});
