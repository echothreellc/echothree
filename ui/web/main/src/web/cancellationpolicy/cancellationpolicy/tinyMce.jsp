<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="CancellationPolicyTranslation" var="cancellationPolicyTranslationEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var policyTAHasEditor = false;
    
    function policyMimeTypeChoiceChange() {
        <c:if test="${cancellationPolicyTranslationEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("policyMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#policyTA' }));
                    policyTAHasEditor = true;
                } else {
                    if(policyTAHasEditor) {
                        tinymce.remove('#policyTA');
                        policyTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        policyMimeTypeChoiceChange();
    }
</script>
