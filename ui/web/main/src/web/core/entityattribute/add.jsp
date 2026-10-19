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
                Add
            </h2>
        </div>
        <div id="Content">
            <et:executionErrors id="errorMessage">
                <p class="executionErrors"><c:out value="${errorMessage}" /></p><br />
            </et:executionErrors>
            <html:form action="/Core/EntityAttribute/Add" method="POST" focus="entityAttributeName">
                <table class="entityAttributeForm">
                    <colgroup>
                        <col class="entityAttributeFormLabel" />
                        <col />
                    </colgroup>
                    <tr>
                        <td align=right><fmt:message key="label.entityAttributeName" />:</td>
                        <td>
                            <html:text property="entityAttributeName" size="40" maxlength="40" />
                            <et:validationErrors id="errorMessage" property="EntityAttributeName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr>
                        <td align=right><fmt:message key="label.entityAttributeType" />:</td>
                        <td>
                            <html:select property="entityAttributeTypeChoice" styleId="entityAttributeTypeChoice">
                                <html:optionsCollection property="entityAttributeTypeChoices" />
                            </html:select> (*)
                            <et:validationErrors id="errorMessage" property="EntityAttributeTypeName">
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
                    <tr data-attribute-types="BLOB">
                        <td align=right><fmt:message key="label.checkContentWebAddress" />:</td>
                        <td>
                            <html:checkbox property="checkContentWebAddress" /> (*)
                            <et:validationErrors id="errorMessage" property="CheckContentWebAddress">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="STRING">
                        <td align=right><fmt:message key="label.validationPattern" />:</td>
                        <td>
                            <html:text property="validationPattern" size="40" maxlength="128" />
                            <et:validationErrors id="errorMessage" property="ValidationPattern">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="LISTITEM MULTIPLELISTITEM">
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
                    <tr data-attribute-types="INTEGER">
                        <td align=right><fmt:message key="label.upperRangeIntegerValue" />:</td>
                        <td>
                            <html:text property="upperRangeIntegerValue" size="12" maxlength="12" />
                            <et:validationErrors id="errorMessage" property="UpperRangeIntegerValue">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="INTEGER">
                        <td align=right><fmt:message key="label.upperLimitIntegerValue" />:</td>
                        <td>
                            <html:text property="upperLimitIntegerValue" size="12" maxlength="12" />
                            <et:validationErrors id="errorMessage" property="UpperLimitIntegerValue">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="INTEGER">
                        <td align=right><fmt:message key="label.lowerLimitIntegerValue" />:</td>
                        <td>
                            <html:text property="lowerLimitIntegerValue" size="12" maxlength="12" />
                            <et:validationErrors id="errorMessage" property="LowerLimitIntegerValue">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="INTEGER">
                        <td align=right><fmt:message key="label.lowerRangeIntegerValue" />:</td>
                        <td>
                            <html:text property="lowerRangeIntegerValue" size="12" maxlength="12" />
                            <et:validationErrors id="errorMessage" property="LowerRangeIntegerValue">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="LONG">
                        <td align=right><fmt:message key="label.upperRangeLongValue" />:</td>
                        <td>
                            <html:text property="upperRangeLongValue" size="20" maxlength="20" />
                            <et:validationErrors id="errorMessage" property="UpperRangeLongValue">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="LONG">
                        <td align=right><fmt:message key="label.upperLimitLongValue" />:</td>
                        <td>
                            <html:text property="upperLimitLongValue" size="20" maxlength="20" />
                            <et:validationErrors id="errorMessage" property="UpperLimitLongValue">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="LONG">
                        <td align=right><fmt:message key="label.lowerLimitLongValue" />:</td>
                        <td>
                            <html:text property="lowerLimitLongValue" size="20" maxlength="20" />
                            <et:validationErrors id="errorMessage" property="LowerLimitLongValue">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="LONG">
                        <td align=right><fmt:message key="label.lowerRangeLongValue" />:</td>
                        <td>
                            <html:text property="lowerRangeLongValue" size="20" maxlength="20" />
                            <et:validationErrors id="errorMessage" property="LowerRangeLongValue">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="INTEGER LONG">
                        <td align=right><fmt:message key="label.unitOfMeasureKindName" />:</td>
                        <td>
                            <html:select property="unitOfMeasureKindChoice">
                                <html:optionsCollection property="unitOfMeasureKindChoices" />
                            </html:select>
                            <et:validationErrors id="errorMessage" property="UnitOfMeasureKindName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
                    <tr data-attribute-types="INTEGER LONG">
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
                    <tr data-attribute-types="WORKFLOW">
                        <td align=right><fmt:message key="label.workflow" />:</td>
                        <td>
                            <html:select property="workflowChoice">
                                <html:optionsCollection property="workflowChoices" />
                            </html:select> <span id="workflowRequired" hidden="hidden">(*)</span>
                            <et:validationErrors id="errorMessage" property="WorkflowName">
                                <p><c:out value="${errorMessage}" /></p>
                            </et:validationErrors>
                        </td>
                    </tr>
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
                            <html:hidden property="componentVendorName" />
                            <html:hidden property="entityTypeName" />
                        </td>
                        <td><html:submit onclick="onSubmitDisable(this);" /><input type="hidden" name="submitButton" /></td>
                    </tr>
                </table>
            </html:form>
        </div>
        <script type="text/javascript">
            (function() {
                var rows = document.querySelectorAll('[data-attribute-types]');
                var entityAttributeTypeChoice = document.getElementById('entityAttributeTypeChoice');
                var workflowChoice = entityAttributeTypeChoice.form.elements['workflowChoice'];
                var unitOfMeasureKindChoice = entityAttributeTypeChoice.form.elements['unitOfMeasureKindChoice'];
                var unitOfMeasureTypeChoice = entityAttributeTypeChoice.form.elements['unitOfMeasureTypeChoice'];

                unitOfMeasureKindChoice.addEventListener('change', function() {
                    unitOfMeasureTypeChoice.value = '';
                    entityAttributeTypeChoice.form.elements['refreshUnitOfMeasureTypes'].value = 'true';
                    entityAttributeTypeChoice.form.submit();
                });

                function updateAttributeFields() {
                    var type = entityAttributeTypeChoice.value;
                    for(var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        var applies = row.getAttribute('data-attribute-types').split(' ').indexOf(type) !== -1;
                        row.hidden = !applies;
                        var inputs = row.querySelectorAll('input, select, textarea');
                        for(var j = 0; j < inputs.length; j++) {
                            inputs[j].disabled = !applies;
                        }
                    }
                    var usesUnits = type === 'INTEGER' || type === 'LONG';
                    unitOfMeasureTypeChoice.disabled = !usesUnits || !unitOfMeasureKindChoice.value;
                    unitOfMeasureTypeChoice.required = usesUnits && !!unitOfMeasureKindChoice.value;
                    document.getElementById('unitOfMeasureTypeRequired').hidden = !unitOfMeasureTypeChoice.required;
                    workflowChoice.required = type === 'WORKFLOW';
                    document.getElementById('workflowRequired').hidden = !workflowChoice.required;
                }

                entityAttributeTypeChoice.addEventListener('change', updateAttributeFields);
                updateAttributeFields();
            })();
        </script>
        <jsp:include page="../../include/userSession.jsp" />
        <jsp:include page="../../include/copyright.jsp" />
    </body>
</html:html>