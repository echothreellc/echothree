<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="ItemDescription" var="partyApplicationEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var clobDescriptionTAHasEditor = false;
    
    function mimeTypeChoiceChange() {
        <c:if test="${partyApplicationEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("mimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#clobDescriptionTA' }));
                    clobDescriptionTAHasEditor = true;
                } else {
                    if(clobDescriptionTAHasEditor) {
                        tinymce.remove('#clobDescriptionTA');
                        clobDescriptionTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        mimeTypeChoiceChange();
    }
</script>
