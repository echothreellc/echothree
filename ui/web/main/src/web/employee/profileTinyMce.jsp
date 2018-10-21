<%@ include file="../include/taglibs.jsp" %>
<%@ include file="../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="EmployeeProfileBio" var="employeeProfileBioEditorUse" commandResultVar="unused" scope="request" />
<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="EmployeeProfileSignature" var="employeeProfileSignatureEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var bioTAHasEditor = false;
    var signatureTAHasEditor = false;
    
    function bioMimeTypeChoiceChange() {
        <c:if test="${employeeProfileBioEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("bioMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#bioTA' }));
                    bioTAHasEditor = true;
                } else {
                    if(bioTAHasEditor) {
                        tinymce.remove('#bioTA');
                        bioTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function signatureMimeTypeChoiceChange() {
        <c:if test="${employeeProfileSignatureEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("signatureMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#signatureTA' }));
                    signatureTAHasEditor = true;
                } else {
                    if(signatureTAHasEditor) {
                        tinymce.remove('#signatureTA');
                        signatureTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        bioMimeTypeChoiceChange();
        signatureMimeTypeChoiceChange();
    }
</script>
