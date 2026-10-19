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
        <title>Entity Attributes</title>
        <html:base/>
        <%@ include file="../../include/environment.jsp" %>
    </head>
    <body>
        <div id="Header">
            <h2>
                <a href="<c:url value="/action/Portal" />"><fmt:message key="navigation.portal" /></a> &gt;&gt;
                <a href="<c:url value="/action/Core/Main" />">Core</a> &gt;&gt;
                <a href="<c:url value="/action/Core/ComponentVendor/Main" />">Component Vendors</a> &gt;&gt;
                <c:url var="entityTypesUrl" value="/action/Core/EntityType/Main">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                </c:url>
                <a href="${entityTypesUrl}">Entity Types</a> &gt;&gt;
                <c:url var="entityAttributesUrl" value="/action/Core/EntityAttribute/Main">
                    <c:param name="ComponentVendorName" value="${componentVendorName}" />
                    <c:param name="EntityTypeName" value="${entityTypeName}" />
                </c:url>
                <a href="${entityAttributesUrl}">Entity Attributes</a> &gt;&gt;
                Edit
            </h2>
        </div>
        <div id="Content">
            <c:choose>
                <c:when test="${commandResult.executionResult.hasLockErrors}">
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                </c:when>
                <c:otherwise>
                    <et:executionErrors id="errorMessage">
                        <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
                    </et:executionErrors>
                    <html:form action="/Core/EntityAttribute/Edit" method="POST" focus="entityAttributeName">
                        <table>
                            <tr>
                                <td align=right><fmt:message key="label.entityAttributeName" />:</td>
                                <td>
                                    <html:text property="entityAttributeName" size="40" maxlength="40" /> (*)
                                    <et:validationErrors id="errorMessage" property="EntityAttributeName">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <tr>
                                <td align=right><fmt:message key="label.trackRevisions" />:</td>
                                <td>
                                    <html:checkbox property="trackRevisions" /> (*)
                                    <et:validationErrors id="errorMessage" property="TrackRevisions">
                                        <p><c:out value="${errorMessage}" /></p>
                                    </et:validationErrors>
                                </td>
                            </tr>
                            <c:if test="${EntityAttributeEdit.entityAttributeTypeChoice == 'BLOB'}">
                                <tr>
                                    <td align=right><fmt:message key="label.checkContentWebAddress" />:</td>
                                    <td>
                                        <html:checkbox property="checkContentWebAddress" /> (*)
                                        <et:validationErrors id="errorMessage" property="CheckContentWebAddress">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${EntityAttributeEdit.entityAttributeTypeChoice == 'STRING'}">
                                <tr>
                                    <td align=right><fmt:message key="label.validationPattern" />:</td>
                                    <td>
                                        <html:text property="validationPattern" size="40" maxlength="128" />
                                        <et:validationErrors id="errorMessage" property="ValidationPattern">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${EntityAttributeEdit.entityAttributeTypeChoice == 'LISTITEM' || EntityAttributeEdit.entityAttributeTypeChoice == 'MULTIPLELISTITEM'}">
                                <tr>
                                    <td align=right><fmt:message key="label.entityListItemSequence" />:</td>
                                    <td>
                                        <html:select property="entityListItemSequenceChoice">
                                            <html:optionsCollection property="entityListItemSequenceChoices" />
                                        </html:select>
                                        <et:validationErrors id="errorMessage" property="EntityListItemSequenceName">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${EntityAttributeEdit.entityAttributeTypeChoice == 'INTEGER'}">
                                <tr>
                                    <td align=right><fmt:message key="label.upperRangeIntegerValue" />:</td>
                                    <td>
                                        <html:text property="upperRangeIntegerValue" size="12" maxlength="12" />
                                        <et:validationErrors id="errorMessage" property="UpperRangeIntegerValue">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                                <tr>
                                    <td align=right><fmt:message key="label.upperLimitIntegerValue" />:</td>
                                    <td>
                                        <html:text property="upperLimitIntegerValue" size="12" maxlength="12" />
                                        <et:validationErrors id="errorMessage" property="UpperLimitIntegerValue">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                                <tr>
                                    <td align=right><fmt:message key="label.lowerLimitIntegerValue" />:</td>
                                    <td>
                                        <html:text property="lowerLimitIntegerValue" size="12" maxlength="12" />
                                        <et:validationErrors id="errorMessage" property="LowerLimitIntegerValue">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                                <tr>
                                    <td align=right><fmt:message key="label.lowerRangeIntegerValue" />:</td>
                                    <td>
                                        <html:text property="lowerRangeIntegerValue" size="12" maxlength="12" />
                                        <et:validationErrors id="errorMessage" property="LowerRangeIntegerValue">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${EntityAttributeEdit.entityAttributeTypeChoice == 'LONG'}">
                                <tr>
                                    <td align=right><fmt:message key="label.upperRangeLongValue" />:</td>
                                    <td>
                                        <html:text property="upperRangeLongValue" size="20" maxlength="20" />
                                        <et:validationErrors id="errorMessage" property="UpperRangeLongValue">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                                <tr>
                                    <td align=right><fmt:message key="label.upperLimitLongValue" />:</td>
                                    <td>
                                        <html:text property="upperLimitLongValue" size="20" maxlength="20" />
                                        <et:validationErrors id="errorMessage" property="UpperLimitLongValue">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                                <tr>
                                    <td align=right><fmt:message key="label.lowerLimitLongValue" />:</td>
                                    <td>
                                        <html:text property="lowerLimitLongValue" size="20" maxlength="20" />
                                        <et:validationErrors id="errorMessage" property="LowerLimitLongValue">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                                <tr>
                                    <td align=right><fmt:message key="label.lowerRangeLongValue" />:</td>
                                    <td>
                                        <html:text property="lowerRangeLongValue" size="20" maxlength="20" />
                                        <et:validationErrors id="errorMessage" property="LowerRangeLongValue">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${EntityAttributeEdit.entityAttributeTypeChoice == 'INTEGER' || EntityAttributeEdit.entityAttributeTypeChoice == 'LONG'}">
                                <tr>
                                    <td align=right><fmt:message key="label.unitOfMeasureKindName" />:</td>
                                    <td>
                                        <html:select property="unitOfMeasureKindChoice" styleId="unitOfMeasureKindChoice">
                                            <html:optionsCollection property="unitOfMeasureKindChoices" />
                                        </html:select>
                                        <et:validationErrors id="errorMessage" property="UnitOfMeasureKindName">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                                <tr>
                                    <td align=right><fmt:message key="label.unitOfMeasureTypeName" />:</td>
                                    <td>
                                        <html:select property="unitOfMeasureTypeChoice">
                                            <html:optionsCollection property="unitOfMeasureTypeChoices" />
                                        </html:select> <span id="unitOfMeasureTypeRequired" hidden="hidden">(*)</span>
                                        <et:validationErrors id="errorMessage" property="UnitOfMeasureTypeName">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                            </c:if>
                            <c:if test="${EntityAttributeEdit.entityAttributeTypeChoice == 'WORKFLOW'}">
                                <tr>
                                    <td align=right><fmt:message key="label.workflow" />:</td>
                                    <td>
                                        <html:select property="workflowChoice">
                                            <html:optionsCollection property="workflowChoices" />
                                        </html:select> (*)
                                        <et:validationErrors id="errorMessage" property="WorkflowName">
                                            <p><c:out value="${errorMessage}" /></p>
                                        </et:validationErrors>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td align=right><fmt:message key="label.sortOrder" />:</td>
                                <td>
                                    <html:text property="sortOrder" size="12" maxlength="12" /> (*)
                                    <et:validationErrors id="errorMessage" property="SortOrder">
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
                                    <input type="hidden" name="refreshUnitOfMeasureTypes" value="false" />
                                    <html:hidden property="entityAttributeTypeChoice" />
                                    <html:hidden property="componentVendorName" />
                                    <html:hidden property="entityTypeName" />
                                    <html:hidden property="originalEntityAttributeName" />
                                </td>
                                <td><html:submit onclick="onSubmitDisable(this);" />&nbsp;<html:reset /><html:hidden property="submitButton" /></td>
                            </tr>
                        </table>
                    </html:form>
                    <c:if test="${EntityAttributeEdit.entityAttributeTypeChoice == 'INTEGER' || EntityAttributeEdit.entityAttributeTypeChoice == 'LONG'}">
                        <script type="text/javascript">
                            (function() {
                                var unitOfMeasureKindChoice = document.getElementById('unitOfMeasureKindChoice');
                                var unitOfMeasureTypeChoice = unitOfMeasureKindChoice.form.elements['unitOfMeasureTypeChoice'];

                                function updateUnitOfMeasureTypeChoice() {
                                    unitOfMeasureTypeChoice.disabled = !unitOfMeasureKindChoice.value;
                                    unitOfMeasureTypeChoice.required = !!unitOfMeasureKindChoice.value;
                                    document.getElementById('unitOfMeasureTypeRequired').hidden = !unitOfMeasureTypeChoice.required;
                                }

                                unitOfMeasureKindChoice.addEventListener('change', function() {
                                    unitOfMeasureTypeChoice.value = '';
                                    unitOfMeasureKindChoice.form.elements['refreshUnitOfMeasureTypes'].value = 'true';
                                    unitOfMeasureKindChoice.form.submit();
                                });
                                unitOfMeasureKindChoice.form.addEventListener('reset', function() {
                                    setTimeout(updateUnitOfMeasureTypeChoice, 0);
                                });
                                updateUnitOfMeasureTypeChoice();
                            })();
                        </script>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../../include/entityLock.jsp" />
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>
