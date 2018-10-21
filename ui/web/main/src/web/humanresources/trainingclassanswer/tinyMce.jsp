<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="TrainingClassAnswerAnswer" var="trainingClassAnswerAnswerEditorUse" commandResultVar="unused" scope="request" />
<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="TrainingClassAnswerSelected" var="trainingClassAnswerSelectedEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var answerTAHasEditor = false;
    var selectedTAHasEditor = false;
    
    function answerMimeTypeChoiceChange() {
        <c:if test="${trainingClassAnswerAnswerEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("answerMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#answerTA' }));
                    answerTAHasEditor = true;
                } else {
                    if(answerTAHasEditor) {
                        tinymce.remove('#answerTA');
                        answerTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function selectedMimeTypeChoiceChange() {
        <c:if test="${trainingClassAnswerSelectedEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("selectedMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#selectedTA' }));
                    selectedTAHasEditor = true;
                } else {
                    if(selectedTAHasEditor) {
                        tinymce.remove('#selectedTA');
                        selectedTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        answerMimeTypeChoiceChange();
        selectedMimeTypeChoiceChange();
    }
</script>
