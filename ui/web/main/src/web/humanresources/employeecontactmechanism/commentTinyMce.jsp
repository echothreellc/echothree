<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="EmployeeContactMechanismComment" var="employeeContactMechanismCommentEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var clobCommentTAHasEditor = false;
    
    function mimeTypeChoiceChange() {
        <c:if test="${employeeContactMechanismCommentEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("mimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#clobCommentTA' }));
                    clobCommentTAHasEditor = true;
                } else {
                    if(clobCommentTAHasEditor) {
                        tinymce.remove('#clobCommentTA');
                        clobCommentTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        mimeTypeChoiceChange();
    }
</script>
