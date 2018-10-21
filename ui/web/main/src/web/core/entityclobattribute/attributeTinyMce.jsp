<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="EntityAttribute" var="entityAttributeEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var clobAttributeTAHasEditor = false;
    
    function mimeTypeChoiceChange() {
        <c:if test="${entityAttributeEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("mimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#clobAttributeTA' }));
                    clobAttributeTAHasEditor = true;
                } else {
                    if(clobAttributeTAHasEditor) {
                        tinymce.remove('#clobAttributeTA');
                        clobAttributeTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        mimeTypeChoiceChange();
    }
</script>
