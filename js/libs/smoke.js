var $ = $ || {};

/*var Smoke = (function ($) {
 "use strict";


 }($));

 */

function ParticleEmitter() {
    this.m_x;
    this.m_y;
    this.m_dieRate;
    this.m_image;
    this.m_speed = 0.02;
    this.m_alpha = 1.0;

    this.m_listParticle = [];

    // ParticleEmitter().init function
    // xScale = number between 0  and 1. 0 = on left side 1 = on top
    // yScale = number between 0  and 1. 0 = on top 1 = on bottom
    // particles = number of particles
    // image = smoke graphic for each particle
    this.init = function (xScale, yScale, particles, image) {
        // the effect is positioned relative to the width and height of the canvas
        this.m_x = CANVAS_WIDTH * xScale;
        this.m_y = CANVAS_HEIGHT * yScale;
        this.m_image = image;
        this.m_dieRate = 1.0;
        // start with smoke already in place
        for (var n = 0; n < particles; n++) {
            this.m_listParticle.push(new Particle());
            this.m_listParticle[n].init(this, n * 50000 * this.m_speed);
        }
    }

    this.destroy = function () {
        for (var n = 0; n < this.m_listParticle.length; n++) {
            this.m_listParticle[n].destroy();
        }
    }


    this.update = function (timeElapsed) {
        for (var n = 0; n < this.m_listParticle.length; n++) {
            this.m_listParticle[n].update(timeElapsed);
        }
    }

    this.render = function (context) {
        for (var n = 0; n < this.m_listParticle.length; n++) {
            this.m_listParticle[n].render(context);
        }
    }
};

function Particle() {
    this.m_x;
    this.m_y;
    this.m_age;
    this.m_xVector;
    this.m_yVector;
    this.m_scale;
    this.m_alpha;
    this.m_canRegen;
    this.m_timeDie;
    this.m_emitter;

    this.init = function (emitter, age) {
        this.isDead = false;
        this.m_age = age;
        this.m_emitter = emitter;
        this.m_canRegen = true;
        this.startRand();
    }

    this.destroy = function () {
        "use strict";
        this.isDead = true;
    }

    this.isAlive = function () {
        return !this.isDead && this.m_age < this.m_timeDie;
    }

    this.startRand = function () {
        // smoke rises and spreads
        this.m_xVector = Math.random() * 0.5 - 0.25;
        this.m_yVector = -1.5 - Math.random();
        this.m_timeDie = 20000 + Math.floor(Math.random() * 12000);

        var invDist = 1.0 / Math.sqrt(this.m_xVector * this.m_xVector
            + this.m_yVector * this.m_yVector);
        // normalise speed
        this.m_xVector = this.m_xVector * invDist * this.m_emitter.m_speed;
        this.m_yVector = this.m_yVector * invDist * this.m_emitter.m_speed;
        // starting position within a 20 pixel area
        this.m_x = (this.m_emitter.m_x + Math.floor(Math.random() * 20) - 10);
        this.m_y = (this.m_emitter.m_y + Math.floor(Math.random() * 20) - 10);
        // the initial age may be > 0. This is so there is already a smoke trail in
        // place at the start
        this.m_x += (this.m_xVector + windVelocity) * this.m_age;
        this.m_y += this.m_yVector * this.m_age;
        this.m_scale = 0.01;
        this.m_alpha = 0.0;
    }

    this.update = function (timeElapsed) {
        this.m_age += timeElapsed;
        if (!this.isAlive()) {
            if (this.isDead) {
                this.m_canRegen = false;
            }
            if (!this.m_canRegen) {
                return;
            }
            // regenerate
            this.m_age = 0;
            this.startRand();
            return;
        }
        // At start the particle fades in and expands rapidly (like in real life)
        var fadeIn = this.m_timeDie * 0.05;
        var startScale;
        var maxStartScale = 0.3;
        if (this.m_age < fadeIn) {
            this.m_alpha = this.m_age / fadeIn;
            startScale = this.m_alpha * maxStartScale;
            // y increases quicker because particle is expanding quicker
            this.m_y += this.m_yVector * 2.0 * timeElapsed;
        }
        else {
            this.m_alpha = 1.0 - (this.m_age - fadeIn) / (this.m_timeDie - fadeIn);
            startScale = maxStartScale;
            this.m_y += this.m_yVector * timeElapsed;
        }
        // the x direction is influenced by wind velocity
        this.m_x += (this.m_xVector + windVelocity) * timeElapsed;
        this.m_alpha *= this.m_emitter.m_alpha;
        this.m_scale = 0.001 + startScale + this.m_age / 4000.0;
    }

    this.render = function (ctx) {
        if (!this.isAlive()) return;
        ctx.globalAlpha = this.m_alpha;
        var height = this.m_emitter.m_image.height * this.m_scale;
        var width = this.m_emitter.m_image.width * this.m_scale;
        // round it to a integer to prevent subpixel positioning
        var x = Math.round(this.m_x - width / 2);
        var y = Math.round(this.m_y + height / 2);
        ctx.drawImage(this.m_emitter.m_image, x, y, width, height);
        if (x < dirtyLeft) {
            dirtyLeft = x;
        }
        if (x + width > dirtyRight) {
            dirtyRight = x + width;
        }
        if (y < dirtyTop) {
            dirtyTop = y;
        }
        if (y + height > dirtyBottom) {
            dirtyBottom = y + height;
        }
    }
};

var lastRender = new Date().getTime();
var context;
var smokeRight = new ParticleEmitter();
var smokeLeft = new ParticleEmitter();
var CANVAS_WIDTH = 1000;
var CANVAS_HEIGHT = 744;
// only redraw minimimum rectangle
var dirtyLeft = 0;
var dirtyTop = 0;
var dirtyRight = CANVAS_WIDTH;
var dirtyBottom = CANVAS_HEIGHT;
var windVelocity = 0.01;
var count = 0;

function initSmoke(canvasId) {
    var canvas = document.getElementById(canvasId);
    if (canvas.getContext) {
        context = canvas.getContext('2d');
    }
    else {
        return;
    }
    var imgSmoke = new Image();
    imgSmoke.src = "img/smoke.png";
    imgSmoke.onload = function () {
        smokeRight.m_alpha = 0.3;
        smokeRight.init(.3, .60, 20, imgSmoke);
        smokeLeft.m_alpha = 0.6;
        smokeLeft.init(.05, .73, 30, imgSmoke);
        requestAnimFrame(render);
    };
}

function destroySmoke() {
    if (smokeRight) {
        smokeRight.destroy();
    }
    if (smokeLeft) {
        smokeLeft.destroy();
    }
}

// shim layer with setTimeout fallback
window.requestAnimFrame = (function () {
    return  window.requestAnimationFrame ||
        window.webkitRequestAnimationFrame ||
        window.mozRequestAnimationFrame ||
        window.oRequestAnimationFrame ||
        window.msRequestAnimationFrame ||
        function (callback) {
            window.setTimeout(callback, 17);
        };
})();


function render() {
    // time in milliseconds
    var timeElapsed = new Date().getTime() - lastRender;
    lastRender = new Date().getTime();
    context.clearRect(dirtyLeft, dirtyTop, dirtyRight - dirtyLeft, dirtyBottom - dirtyTop);
    dirtyLeft = 1000;
    dirtyTop = 1000;
    dirtyRight = 0;
    dirtyBottom = 0;
    smokeRight.update(timeElapsed);
    smokeRight.render(context);
    smokeLeft.update(timeElapsed);
    smokeLeft.render(context);
    windVelocity += (Math.random() - 0.5) * 0.002;
    if (windVelocity > 0.015) {
        windVelocity = 0.015;
    }
    if (windVelocity < 0.0) {
        windVelocity = 0.0;
    }
    requestAnimFrame(render);
}

