<%@ include file="../../include/taglibs.jsp" %>
<%@ include file="../../include/tinyMce.jsp" %>

<c:choose>
    <c:when test='${forumMessage.forumMessageType.forumMessageTypeName == "BLOG_ENTRY"}'>
        <et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="BlogEntryFeedSummary" var="blogFeedSummaryEditor" commandResultVar="unused" scope="request" />
        <et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="BlogEntrySummary" var="blogSummaryEditor" commandResultVar="unused" scope="request" />
        <et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="BlogEntryContent" var="blogContentEditor" commandResultVar="unused" scope="request" />
    </c:when>
    <c:when test='${parentForumMessage != null || forumMessage.forumMessageType.forumMessageTypeName == "BLOG_COMMENT"}'>
        <et:partyApplicationEditorUse applicationName="main" applicationEditorUseName="BlogCommentContent" var="blogContentEditor" commandResultVar="unused" scope="request" />
    </c:when>
</c:choose>

<script type="text/javascript">
    var contentTAHasEditor = false;
    
    <c:if test='${forumMessage.forumMessageType.forumMessageTypeName == "BLOG_ENTRY"}'>
        var feedSummaryTAHasEditor = false;
        var summaryTAHasEditor = false;
        
        function feedSummaryMimeTypeChoiceChange() {
            <c:if test="${blogFeedSummaryEditor.applicationEditor.editor.editorName == 'TINYMCE'}">
                var choicesObj = document.getElementById("feedSummaryMimeTypeChoices");

                if(choicesObj !== null) {
                    var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                    if(mimeType === 'text/html') {
                        tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#feedSummaryTA' }));
                        feedSummaryTAHasEditor = true;
                    } else {
                        if(feedSummaryTAHasEditor) {
                            tinymce.remove('#feedSummaryTA');
                            feedSummaryTAHasEditor = false;
                        }
                    }
                }
            </c:if>
        }

        function summaryMimeTypeChoiceChange() {
            <c:if test="${blogSummaryEditor.applicationEditor.editor.editorName == 'TINYMCE'}">
                var choicesObj = document.getElementById("summaryMimeTypeChoices");

                if(choicesObj !== null) {
                    var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                    if(mimeType === 'text/html') {
                        tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#summaryTA' }));
                        summaryTAHasEditor = true;
                    } else {
                        if(summaryTAHasEditor) {
                            tinymce.remove('#summaryTA');
                            summaryTAHasEditor = false;
                        }
                    }
                }
            </c:if>
        }
    </c:if>

    function contentMimeTypeChoiceChange() {
        <c:if test="${blogContentEditor.applicationEditor.editor.editorName == 'TINYMCE'}">
            var choicesObj = document.getElementById("contentMimeTypeChoices");

            if(choicesObj !== null) {
                var mimeType = choicesObj.options[choicesObj.selectedIndex].value;

                if(mimeType === 'text/html') {
                    tinymce.init($.extend({}, globalTinyMceProperties, { selector : '#contentTA' }));
                    contentTAHasEditor = true;
                } else {
                    if(contentTAHasEditor) {
                        tinymce.remove('#contentTA');
                        contentTAHasEditor = false;
                    }
                }
            }
        </c:if>
    }

    function pageLoaded() {
        <c:if test='${forumMessage.forumMessageType.forumMessageTypeName == "BLOG_ENTRY"}'>
            feedSummaryMimeTypeChoiceChange();
            summaryMimeTypeChoiceChange();
        </c:if>
        contentMimeTypeChoiceChange();
    }
</script>
