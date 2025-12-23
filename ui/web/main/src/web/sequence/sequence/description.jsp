<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2026 Echo Three, LLC                                              -->
<!--                                                                                  -->
<!-- Licensed under the Apache License, Version 2.0 (the "License");                  -->
<!-- you may not use this file except in compliance with the License.                 -->
<!-- You may obtain a copy of the License at                                          -->
<!--                                                                                  -->
<!--     http://www.apache.org/licenses/LICENSE-2.0                                   -->
<!--                                                                                  -->
<!-- Unless required by applicable law or agreed to in writing, software              -->
<!-- distributed under the License is distributed on an "AS IS" BASIS,                -->
<!-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.         -->
<!-- See the License for the specific language governing permissions and              -->
<!-- limitations under the License.                                                   -->
<!--                                                                                  -->

<%@ include file="../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Sequence Descriptions</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Sequence/Main" />">Sequences</a> &gt;&gt;
                <a href="<c:url value="/action/Sequence/SequenceType/Main" />">Types</a> &gt;&gt;
                <c:url var="sequencesUrl" value="/action/Sequence/Sequence/Main">
                    <c:param name="SequenceTypeName" value="${sequenceType.sequenceTypeName}" />
                </c:url>
                <a href="${sequencesUrl}">Sequences</a> &gt;&gt;
                Descriptions
            </h2>
        </div>
        <div id="Content">
            <c:url var="addDescriptionUrl" value="/action/Sequence/Sequence/DescriptionAdd">
                <c:param name="SequenceTypeName" value="${sequenceType.sequenceTypeName}" />
                <c:param name="SequenceName" value="${sequence.sequenceName}" />
            </c:url>
            <p><a href="${addDescriptionUrl}">Add Description.</a></p>
            <display:table name="sequenceDescriptions" id="sequenceDescription" class="displaytag">
                <display:column titleKey="columnTitle.language">
                    <c:out value="${sequenceDescription.language.description}" />
                </display:column>
                <display:column titleKey="columnTitle.description">
                    <c:out value="${sequenceDescription.description}" />
                </display:column>
                <display:column>
                    <c:url var="editDescriptionUrl" value="/action/Sequence/Sequence/DescriptionEdit">
                        <c:param name="SequenceTypeName" value="${sequenceDescription.sequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${sequenceDescription.sequence.sequenceName}" />
                        <c:param name="LanguageIsoName" value="${sequenceDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${editDescriptionUrl}">Edit</a>
                    <c:url var="deleteDescriptionUrl" value="/action/Sequence/Sequence/DescriptionDelete">
                        <c:param name="SequenceTypeName" value="${sequenceDescription.sequence.sequenceType.sequenceTypeName}" />
                        <c:param name="SequenceName" value="${sequenceDescription.sequence.sequenceName}" />
                        <c:param name="LanguageIsoName" value="${sequenceDescription.language.languageIsoName}" />
                    </c:url>
                    <a href="${deleteDescriptionUrl}">Delete</a>
                </display:column>
           </display:table>
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
