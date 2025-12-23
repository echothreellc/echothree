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

<%@ include file="../../../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Selector Nodes</title>
        <html:base/>
        <%@ include file="../../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Selector/Main" />">Selectors</a> &gt;&gt;
                <a href="<c:url value="/action/Selector/SelectorKind/Main" />">Kinds</a> &gt;&gt;
                <c:url var="selectorTypesUrl" value="/action/Selector/SelectorKind/Main">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                </c:url>
                <a href="${selectorTypesUrl}">Types</a> &gt;&gt;
                <c:url var="selectorsUrl" value="/action/Selector/Selector/Main">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorTypeName}" />
                </c:url>
                <a href="${selectorsUrl}">Selectors</a> &gt;&gt;
                <c:url var="selectorNodesUrl" value="/action/Selector/SelectorNode/Main">
                    <c:param name="SelectorKindName" value="${selectorKindName}" />
                    <c:param name="SelectorTypeName" value="${selectorTypeName}" />
                    <c:param name="SelectorName" value="${selectorName}" />
                </c:url>
                <a href="${selectorNodesUrl}">Nodes</a> &gt;&gt;
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            Adding <c:out value="${selectorNodeType.description}" /> Selector Node.
            <c:if test='${workflowStep != null}'>
                <br />
                Workflow: <c:out value="${workflowStep.workflow.description}" />,
                Workflow Step: <c:out value="${workflowStep.description}" />
            </c:if>
            <c:if test='${entityListItem != null}'>
                <br />
                Component Vendor: <c:out value="${entityListItem.entityAttribute.entityType.componentVendor.description}" />,
                Entity Type: <c:out value="${entityListItem.entityAttribute.entityType.description}" />,
                Entity Attribute <c:out value="${entityListItem.entityAttribute.description}" />,
                Entity List Item <c:out value="${entityListItem.description}" />
            </c:if>
            <br />
            <br />
            <html:form action="/Selector/SelectorNode/Add/FinalStep" method="POST" focus="selectorNodeName">
                <table>
                    <tr>
                        <td align=right><fmt:message key="label.selectorNodeName" />:</td>
                        <td>
                            <html:text property="selectorNodeName" size="40" maxlength="40" /> (*)
                            <et:validationErrors id="errorMessage" property="SelectorNodeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.isRootSelectorNode" />:</td>
                        <td>
                            <html:checkbox property="isRootSelectorNode" /> (*)
                            <et:validationErrors id="errorMessage" property="IsRootSelectorNode">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <c:choose>
                        <c:when test='${selectorNodeTypeName == "BOOLEAN"}'>
                            <tr>
                                <td align=right><fmt:message key="label.selectorBooleanType" />:</td>
                                <td>
                                    <html:select property="selectorBooleanTypeChoice">
                                        <html:optionsCollection property="selectorBooleanTypeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="SelectorBooleanTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.leftSelectorNode" />:</td>
                                <td>
                                    <html:select property="leftSelectorNodeChoice">
                                        <html:optionsCollection property="leftSelectorNodeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="LeftSelectorNodeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.rightSelectorNode" />:</td>
                                <td>
                                    <html:select property="rightSelectorNodeChoice">
                                        <html:optionsCollection property="rightSelectorNodeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="RightSelectorNodeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test='${selectorNodeTypeName == "RESPONSIBILITY_TYPE"}'>
                            <tr>
                                <td align=right><fmt:message key="label.responsibilityType" />:</td>
                                <td>
                                    <html:select property="responsibilityTypeChoice">
                                        <html:optionsCollection property="responsibilityTypeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="ResponsibilityTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test='${selectorNodeTypeName == "SKILL_TYPE"}'>
                            <tr>
                                <td align=right><fmt:message key="label.skillType" />:</td>
                                <td>
                                    <html:select property="skillTypeChoice">
                                        <html:optionsCollection property="skillTypeChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="SkillTypeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test='${selectorNodeTypeName == "TRAINING_CLASS"}'>
                            <tr>
                                <td align=right><fmt:message key="label.trainingClass" />:</td>
                                <td>
                                    <html:select property="trainingClassChoice">
                                        <html:optionsCollection property="trainingClassChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="TrainingClassName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test='${selectorNodeTypeName == "ITEM_CATEGORY"}'>
                            <tr>
                                <td align=right><fmt:message key="label.itemCategory" />:</td>
                                <td>
                                    <html:select property="itemCategoryChoice">
                                        <html:optionsCollection property="itemCategoryChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="ItemCategoryName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test='${selectorNodeTypeName == "ITEM_ACCOUNTING_CATEGORY"}'>
                            <tr>
                                <td align=right><fmt:message key="label.itemAccountingCategory" />:</td>
                                <td>
                                    <html:select property="itemAccountingCategoryChoice">
                                        <html:optionsCollection property="itemAccountingCategoryChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="ItemAccountingCategoryName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test='${selectorNodeTypeName == "ITEM_PURCHASING_CATEGORY"}'>
                            <tr>
                                <td align=right><fmt:message key="label.itemPurchasingCategory" />:</td>
                                <td>
                                    <html:select property="itemPurchasingCategoryChoice">
                                        <html:optionsCollection property="itemPurchasingCategoryChoices" />
                                    </html:select> (*)
                                    <et:validationErrors id="errorMessage" property="ItemPurchasingCategoryName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                        </c:when>
                    </c:choose>
                    <c:if test='${selectorNodeTypeName == "ITEM_CATEGORY" || selectorNodeTypeName == "ITEM_ACCOUNTING_CATEGORY" || selectorNodeTypeName == "ITEM_PURCHASING_CATEGORY"}'>
                        <tr>
                            <td align=right><fmt:message key="label.checkParents" />:</td>
                            <td>
                                <html:checkbox property="checkParents" /> (*)
                                <et:validationErrors id="errorMessage" property="CheckParents">
                                    <p><c:out value="${errorMessage}" /></p>
                                </et:validationErrors>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td align=right><fmt:message key="label.negate" />:</td>
                        <td>
                            <html:checkbox property="negate" /> (*)
                            <et:validationErrors id="errorMessage" property="Negate">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.description" />:</td>
                        <td>
                            <html:text property="description" size="60" maxlength="132" />
                            <et:validationErrors id="errorMessage" property="Description">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <html:hidden property="selectorKindName" />
                            <html:hidden property="selectorTypeName" />
                            <html:hidden property="selectorName" />
                            <html:hidden property="selectorNodeTypeName" />
                            <html:hidden property="workflowName" />
                            <html:hidden property="workflowStepName" />
                            <html:hidden property="componentVendorName" />
                            <html:hidden property="entityTypeName" />
                            <html:hidden property="entityAttributeName" />
                            <html:hidden property="entityListItemName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <jsp:include page="../../../include/userSession.jsp" />
        <jsp:include page="../../../include/copyright.jsp" />
    </body>
</html:html>
