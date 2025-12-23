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
        <title>
            <fmt:message key="pageTitle.itemDescriptionType">
                <fmt:param value="${itemDescriptionType.itemDescriptionTypeName}" />
            </fmt:message>
        </title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/Main" />"><fmt:message key="navigation.items" /></a> &gt;&gt;
                <a href="<c:url value="/action/Item/ItemDescriptionType/Main" />"><fmt:message key="navigation.itemDescriptionTypes" /></a> &gt;&gt;
                <fmt:message key="navigation.itemDescriptionType">
                    <fmt:param value="${itemDescriptionType.itemDescriptionTypeName}" />
                </fmt:message>
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${itemDescriptionType.itemDescriptionTypeName != itemDescriptionType.description}">
                    <p><font size="+2"><b><et:appearance appearance="${itemDescriptionType.entityInstance.entityAppearance.appearance}"><c:out value="${itemDescriptionType.description}" /></et:appearance></b></font></p>
                    <p><font size="+1"><et:appearance appearance="${itemDescriptionType.entityInstance.entityAppearance.appearance}"><c:out value="${itemDescriptionType.itemDescriptionTypeName}" /></et:appearance></font></p>
                </c:when>
                <c:otherwise>
                    <p><font size="+2"><b><et:appearance appearance="${itemDescriptionType.entityInstance.entityAppearance.appearance}"><c:out value="${itemDescriptionType.itemDescriptionTypeName}" /></et:appearance></b></font></p>
                </c:otherwise>
            </c:choose>
            <br />
            Item Description Type Name: ${itemDescriptionType.itemDescriptionTypeName}<br />
            <br />
            Use Parent If Missing:
            <c:choose>
                <c:when test="${itemDescriptionType.useParentIfMissing}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <c:if test='${itemDescriptionType.mimeTypeUsageType != null}'>
                Mime Type Usage Type: ${itemDescriptionType.mimeTypeUsageType.description}<br />
            </c:if>
            Check Content Web Address:
            <c:choose>
                <c:when test="${itemDescriptionType.checkContentWebAddress}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Include In Index:
            <c:choose>
                <c:when test="${itemDescriptionType.includeInIndex}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            Index Default:
            <c:choose>
                <c:when test="${itemDescriptionType.indexDefault}">
                    <fmt:message key="phrase.yes" />
                </c:when>
                <c:otherwise>
                    <fmt:message key="phrase.no" />
                </c:otherwise>
            </c:choose>
            <br />
            <br />
            <c:choose>
                <c:when test="${itemDescriptionType.mimeTypeUsageType.mimeTypeUsageTypeName == 'IMAGE'}">
                   Minimum Height:
                    <c:choose>
                        <c:when test='${itemDescriptionType.minimumHeight == null}'>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemDescriptionType.minimumHeight}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                   Minimum Width:
                    <c:choose>
                        <c:when test='${itemDescriptionType.minimumWidth == null}'>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemDescriptionType.minimumWidth}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                   Maximum Height:
                    <c:choose>
                        <c:when test='${itemDescriptionType.maximumHeight == null}'>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemDescriptionType.maximumHeight}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                   Maximum Width:
                    <c:choose>
                        <c:when test='${itemDescriptionType.maximumWidth == null}'>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemDescriptionType.maximumWidth}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                   Preferred Height:
                    <c:choose>
                        <c:when test='${itemDescriptionType.preferredHeight == null}'>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemDescriptionType.preferredHeight}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                   Preferred Width:
                    <c:choose>
                        <c:when test='${itemDescriptionType.preferredWidth == null}'>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemDescriptionType.preferredWidth}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                   Preferred Mime Type:
                    <c:choose>
                        <c:when test='${itemDescriptionType.preferredMimeType == null}'>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            <c:url var="mimeTypeUrl" value="/action/Core/MimeType/Review">
                                <c:param name="MimeTypeName" value="${itemDescriptionType.preferredMimeType.mimeTypeName}" />
                            </c:url>
                            <a href="${mimeTypeUrl}"><c:out value="${itemDescriptionType.preferredMimeType.description}" /></a>
                        </c:otherwise>
                    </c:choose>
                    <br />
                   Quality:
                    <c:choose>
                        <c:when test='${itemDescriptionType.quality == null}'>
                            <i><fmt:message key="phrase.notSet" /></i>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${itemDescriptionType.quality}" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                    Scale from Parent:
                    <c:choose>
                        <c:when test="${itemDescriptionType.scaleFromParent}">
                            <fmt:message key="phrase.yes" />
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="phrase.no" />
                        </c:otherwise>
                    </c:choose>
                    <br />
                </c:when>
            </c:choose>
            <br />            
            <br />
            <c:set var="entityInstance" scope="request" value="${itemDescriptionType.entityInstance}" />
            <jsp:include page="../../include/entityInstance.jsp" />
        </div>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
