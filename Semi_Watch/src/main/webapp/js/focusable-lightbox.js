function Modal(element) {
    this.lightbox = element;
    this.focusableElements = element.querySelectorAll("button:not([disabled]), a[href]:not([disabled]), input:not([disabled]), select:not([disabled]), textarea:not([disabled]), [tabindex]:not([tabindex='-1'])");
    this.focusableElements = Array.prototype.slice.call(this.focusableElements).filter(function(element) {
        if (element.matches(".image-box__src")) {
            element.removeAttribute("tabindex");
        } else {
            return element;
        }
    });
    this.focusableElements[0].focus();
    this.actualIndex = 0;
    this.lastElementIndex = this.focusableElements.length - 1;
    this.keysPressed = {};

    var self = this;
    this.handlerPressed = function(event) {
        self.keyPressed(event);
    };
    this.handlerReleased = function(event) {
        self.keyReleased(event);
    };

    this.lightbox.attachEvent("onkeydown", this.handlerPressed);
    this.lightbox.attachEvent("onkeyup", this.handlerReleased);
}

Modal.prototype.keyPressed = function(event) {
    this.keysPressed[event.key] = true;
    if (this.keysPressed['Shift'] && this.keysPressed['Tab']) {
        if (this.actualIndex === 0) {
            event.returnValue = false; // preventDefault
            this.actualIndex = this.lastElementIndex;
            this.focusableElements[this.actualIndex].focus();
        } else {
            this.actualIndex = this.actualIndex - 1;
        }
    } else if (this.keysPressed['Tab']) {
        if (this.actualIndex === this.lastElementIndex) {
            event.returnValue = false; // preventDefault
            this.actualIndex = 0;
            this.focusableElements[this.actualIndex].focus();
        } else {
            this.actualIndex = this.actualIndex + 1;
        }
    } else if (this.keysPressed['Escape']) {
        var self = this;
        setTimeout(function() {
            self.lightbox.style.display = '';
            self.removeEvents();
            mainJs.toggleDocumentOverflow();
        }, 200);
    }
};

Modal.prototype.keyReleased = function(event) {
    delete this.keysPressed[event.key];
};

Modal.prototype.removeEvents = function() {
    this.lightbox.detachEvent("onkeydown", this.handlerPressed);
    this.lightbox.detachEvent("onkeyup", this.handlerReleased);
};
