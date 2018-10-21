<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="CompanyDocument" var="companyDocumentEditorUse" commandResultVar="unused" scope="request" />

<script type="text/javascript">
    var clobTAHasEditor = false;
    
    function mimeTypeChoiceChange() {
        <c:if test="${companyDocumentEditorUse.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("mimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#clobTA' }));
                    clobTAHasEditor = true;
                } else {
                    if(clobTAHasEditor) {
                        tinymce.remove('#clobTA');
                        clobTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        mimeTypeChoiceChange();
    }
</script>
