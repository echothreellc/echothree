const jumpTarget = document.forms["Jump"].elements["target"];

function setJumpPlaceholder() {
    jumpTarget.setAttribute("placeholder", "Search...");
}

setJumpPlaceholder();
$(jumpTarget).on("focus", function() {
    this.setAttribute("placeholder", "");
}).on("blur", setJumpPlaceholder);

$(document).keypress(function(e) {
    if(e.charCode === 96) {
        const originalEvent = e.originalEvent || e;
        const isAltGraph = originalEvent.getModifierState && originalEvent.getModifierState("AltGraph");
        const hasShortcutModifier = e.metaKey || (!isAltGraph && (e.altKey || e.ctrlKey));

        if(!hasShortcutModifier) {
            const tagName = document.activeElement.tagName;

            if(tagName !== 'INPUT' && tagName !== 'TEXTAREA') {
                jumpTarget.focus();
                e.preventDefault();
            }
        }
    }
});
