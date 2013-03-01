// @codekit-prepend "libs/jquery.js"
// @codekit-prepend "libs/impress.js"
// @codekit-prepend "libs/impress.console.js"
// @codekit-prepend "libs/rainbow.js"
// @codekit-prepend "libs/language/c.js"
// @codekit-prepend "libs/language/coffeescript.js"
// @codekit-prepend "libs/language/csharp.js"
// @codekit-prepend "libs/language/css.js"
// @codekit-prepend "libs/language/generic.js"
// @codekit-prepend "libs/language/go.js"
// @codekit-prepend "libs/language/haskell.js"
// @codekit-prepend "libs/language/html.js"
// @codekit-prepend "libs/language/java.js"
// @codekit-prepend "libs/language/javascript.js"
// @codekit-prepend "libs/language/lua.js"
// @codekit-prepend "libs/language/php.js"
// @codekit-prepend "libs/language/python.js"
// @codekit-prepend "libs/language/r.js"
// @codekit-prepend "libs/language/ruby.js"
// @codekit-prepend "libs/language/scheme.js"
// @codekit-prepend "libs/language/shell.js"
// @codekit-prepend "libs/language/smalltalk.js"
// @codekit-prepend "libs/smoke.js"

(function () {
    "use strict";

    var Factory = {

        /**
         * Attaches key handlers to enter full screen.
         */
        enterFullScreen : function () {
            $(document).keypress(function (e) {
                if (e.keyCode === 102) {
                    document.getElementsByTagName("html")[0].webkitRequestFullScreen();
                }
            });
        },

        /**
         * Loads the demo for the current step into an iframe.
         */
        loadDemo : function (step) {
            if (step.data("demo-loaded")) {
                return;
            }

            var browser = step.find(".browser");
            var src = browser.data("src");
            browser.append("<iframe src='" + src + "' frameborder='0'></iframe>");

            step.data("demo-loaded", true);
        },

        /**
         * Loads the source code for the current step.
         */
        loadSrc : function (step) {
            if (step.data("src-loaded")) {
                return;
            }

            $("code[data-src]", step).each(function (index, element) {
                var $element = $(element);
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
        },

        /**
         * Starts the smoke effect.
         */
        startSmoke : function (step) {
            initSmoke(step + "-smoke");
        },

        /**
         * Stops the smoke effect.
         */
        stopSmoke : function () {
            setTimeout(function () {
                destroySmoke();
            }, 5000);
        }
    };

    $(function () {
        $(".step").on("impress:stepenter", function () {
            Factory.loadSrc($(this));
            Factory.loadDemo($(this));
        });

        $("#intro-1").on("impress:stepenter", function () {
            Factory.startSmoke("intro");
        });
        $("#intro-1").on("impress:stepleave", function () {
            Factory.stopSmoke("intro");
        });
        $("#outro-1").on("impress:stepenter", function () {
            Factory.startSmoke("outro");
        });
        $("#outro-1").on("impress:stepleave", function () {
            Factory .stopSmoke("outro");
        });

        Factory.enterFullScreen();
    });
}());
