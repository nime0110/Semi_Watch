function Modal(element) {
    this.$lightbox = $(element);
    this.$focusableElements = this.$lightbox.find("button:not([disabled]), a[href]:not([disabled]), input:not([disabled]), select:not([disabled]), textarea:not([disabled]), [tabindex]:not([tabindex='-1'])");

    this.$focusableElements = this.$focusableElements.filter(function(index, element) {
        if ($(element).is(".image-box__src")) {
            $(element).removeAttr("tabindex");
        } else {
            return element;
        }
    });

    this.$focusableElements.first().focus();
    this.actualIndex = 0;
    this.lastElementIndex = this.$focusableElements.length - 1;
    this.keysPressed = {};

    var self = this;
    this.handlerPressed = function(event) {
        self.keyPressed(event);
    };
    this.handlerReleased = function(event) {
        self.keyReleased(event);
    };

    this.$lightbox.on("keydown", this.handlerPressed);
    this.$lightbox.on("keyup", this.handlerReleased);
}

Modal.prototype.keyPressed = function(event) {
    this.keysPressed[event.key] = true;
    if (this.keysPressed['Shift'] && this.keysPressed['Tab']) {
        if (this.actualIndex === 0) {
            event.preventDefault();
            this.actualIndex = this.lastElementIndex;
            this.$focusableElements.eq(this.actualIndex).focus();
        } else {
            this.actualIndex -= 1;
        }
    } else if (this.keysPressed['Tab']) {
        if (this.actualIndex === this.lastElementIndex) {
            event.preventDefault();
            this.actualIndex = 0;
            this.$focusableElements.eq(this.actualIndex).focus();
        } else {
            this.actualIndex += 1;
        }
    } else if (this.keysPressed['Escape']) {
        var self = this;
        setTimeout(function() {
            self.$lightbox.hide();
            self.removeEvents();
            mainJs.toggleDocumentOverflow();
        }, 200);
    }
};

Modal.prototype.keyReleased = function(event) {
    delete this.keysPressed[event.key];
};

Modal.prototype.removeEvents = function() {
    this.$lightbox.off("keydown", this.handlerPressed);
    this.$lightbox.off("keyup", this.handlerReleased);
};
