<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="TrainingClassQuestionQuestion" var="trainingClassQuestionQuestionEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var questionTAHasEditor = false;
    
    function questionMimeTypeChoiceChange() {
        <c:if test="${trainingClassQuestionQuestionEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("questionMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#questionTA' }));
                    questionTAHasEditor = true;
                } else {
                    if(questionTAHasEditor) {
                        tinymce.remove('#questionTA');
                        questionTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        questionMimeTypeChoiceChange();
    }
</script>
