/**
 * impressConsole.js
 *
 * Adds a presenter console to impress.js
 *
 * MIT Licensed, see license.txt.
 *
 * Copyright 2012 impress-console contributors (see README.txt)
 *
 * version: 1.0b3-dev
 *
 */

(function (document, window) {
    'use strict';

    // This is the default template for the speaker console window
    var consoleTemplate = '<!DOCTYPE html>' +
        '<html class="ic"><head>' +
        '<link rel="stylesheet" type="text/css" media="screen" href="css/libs/impress.console.css">' +
        '</head><body>' +
        '<div id="console">' +
        '<div id="notes"></div>' +
        '</div>' +
        '</body></html>';

    // All console windows, so that you can call console() repeatedly.
    var allConsoles = {};

    // The console object
    var console = window.console = function (rootId) {

        rootId = rootId || 'impress';

        if (allConsoles[rootId]) {
            return allConsoles[rootId];
        }

        // root presentation elements
        var root = document.getElementById(rootId);

        var consoleWindow = null;

        // Sync the notes to the step
        var onStepLeave = function () {
            if (consoleWindow) {
                // Set notes to next steps notes.
                var newNotes = document.querySelector('.active').querySelector('.notes');
                if (newNotes) {
                    newNotes = newNotes.innerHTML;
                } else {
                    newNotes = 'No notes for this step';
                }
                consoleWindow.document.getElementById('notes').innerHTML = newNotes;
            }
        };

        // Sync the previews to the step
        var onStepEnter = function () {
            if (consoleWindow) {
                // We do everything here again, because if you stopped the previos step to
                // early, the onstepleave trigger is not called for that step, so
                // we need this to sync things.
                var newNotes = document.querySelector('.active').querySelector('.notes');
                if (newNotes) {
                    newNotes = newNotes.innerHTML;
                } else {
                    newNotes = 'No notes for this step';
                }
                consoleWindow.document.getElementById('notes').innerHTML = newNotes;
            }
        };

        var open = function () {
            if (top.isconsoleWindow) {
                return;
            }

            if (consoleWindow && !consoleWindow.closed) {
                consoleWindow.focus();
            } else {
                consoleWindow = window.open();
                // This sets the window location to the main window location, so css can be loaded:
                consoleWindow.document.open();
                // Write the template:
                consoleWindow.document.write(consoleTemplate);
                consoleWindow.document.title = 'Speaker Console (' + document.title + ')';
                consoleWindow.impress = window.impress;
                // We set this flag so we can detect it later, to prevent infinite popups.
                consoleWindow.isconsoleWindow = true;

                // Show the current slide
                onStepLeave();
                onStepEnter();
            }
        };

        var init = function () {
            // Register the event
            root.addEventListener('impress:stepleave', onStepLeave)
            root.addEventListener('impress:stepenter', onStepEnter)

            //When the window closes, clean up after ourselves.
            window.onunload = function () {
                consoleWindow && !consoleWindow.closed && consoleWindow.close();
            };

            //Open speaker console when they press 'n'
            document.addEventListener('keyup', function (event) {
                if (event.keyCode === 78) {
                    open();
                }

            }, false);

        }

        // Return the object
        return allConsoles[rootId] = {init : init, open : open}

    }

})(document, window);

