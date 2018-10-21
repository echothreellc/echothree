<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="TrainingClassPagePage" var="trainingClassPagePageEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var pageTAHasEditor = false;
    
    function pageMimeTypeChoiceChange() {
        <c:if test="${trainingClassPagePageEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("pageMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#pageTA' }));
                    pageTAHasEditor = true;
                } else {
                    if(pageTAHasEditor) {
                        tinymce.remove('#pageTA');
                        pageTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        pageMimeTypeChoiceChange();
    }
</script>
