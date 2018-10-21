<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="HarmonizedTariffScheduleCodeOverview" var="harmonizedTariffScheduleCodeOverviewEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var overviewTAHasEditor = false;
    
    function overviewMimeTypeChoiceChange() {
        <c:if test="${harmonizedTariffScheduleCodeOverviewEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
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

    function pageLoaded() {
        overviewMimeTypeChoiceChange();
    }
</script>
