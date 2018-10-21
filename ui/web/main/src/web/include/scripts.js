var submitting = false;

function onSubmitDisable(obj) {
    if(submitting === false) {
        submitting = true;

        var frm = obj.form;
        for(var fld = 0; fld < frm.elements.length; fld++) {
            var elt = frm.elements[fld];
            if((elt.type === "submit") || (elt.type === "reset")) {
                elt.disabled = true;
            }
        }
        frm.elements["submitButton"].value = obj.name;

        frm.submit();
    }
}

var entityLockStatusEntity;

function entityLockStatusToTimedOut() {
    entityLockStatusEntity.css("backgroundColor","#CC0000");
    entityLockStatusEntity.css("color","#000000");
    entityLockStatusEntity.html('&nbsp;Timed Out&nbsp;');
}

function entityLockStatusToCaution(remainingTimeout) {
    entityLockStatusEntity.css("backgroundColor","#FFFF00");
    entityLockStatusEntity.html('&nbsp;Caution&nbsp;');

    setTimeout('entityLockStatusToTimedOut();', remainingTimeout);
}

function startEntityLockStatus(lockTimeout, entityRef) {
    entityLockStatusEntity = $("#entityLockStatus");
    remainingTimeout = lockTimeout - 60000;

    if(remainingTimeout > 60000) {
        entityLockStatusEntity.css("backgroundColor","#00CC00");
        entityLockStatusEntity.html('&nbsp;OK&nbsp;');

        setTimeout('entityLockStatusToCaution(60000);', remainingTimeout);
    } else {
        if(remainingTimeout > 0) {
            entityLockStatusToCaution(remainingTimeout);
        }
    }
    
    $(window).on("unload", function(e) {
        if(!submitting) {
            $.ajax({
                url: '../../action/Core/EntityInstance/Unlock?EntityRef=' + entityRef,
                async: false
            });
        }
    });
}

function doIdle(url) {
    $.ajax({
        url: url
    });
}

function startIdle(url) {
    setInterval('doIdle("' + url + '");', 600000);
}
