<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="TrainingClassOverview" var="trainingClassOverviewEditorUse" commandResultVar="unused" scope="request" />
<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="TrainingClassIntroduction" var="trainingClassIntroductionEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var overviewTAHasEditor = false;
    var introductionTAHasEditor = false;
    
    function overviewMimeTypeChoiceChange() {
        <c:if test="${trainingClassOverviewEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("overviewMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#overviewTA' }));
                    overviewTAHasEditor = true;
                } else {
                    if(overviewTAHasEditor) {
                        tinymce.remove('#overviewTA');
                        overviewTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function introductionMimeTypeChoiceChange() {
        <c:if test="${trainingClassIntroductionEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("introductionMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#introductionTA' }));
                    introductionTAHasEditor = true;
                } else {
                    if(introductionTAHasEditor) {
                        tinymce.remove('#introductionTA');
                        introductionTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        overviewMimeTypeChoiceChange();
        introductionMimeTypeChoiceChange();
    }
</script>
