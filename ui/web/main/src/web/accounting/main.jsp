<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2019 Echo Three, LLC                                              -->
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

<%@ include file="../include/taglibs.jsp" %>

<html>
    <head>
        <title><fmt:message key="pageTitle.accounting" /></title>
        <%@ include file="../include/environment-b.jsp" %>
    </head>
    <%@ include file="../include/body-start-b.jsp" %>
        <et:checkSecurityRoles securityRoles="Company.List:GlAccount.List:GlAccountCategory.List:GlAccountClass.List:GlResourceType.List:Tax.List:Term.List:TransactionType.List:TransactionGroup.List:Currency.List:ItemAccountingCategory.List" />
        <%@ include file="../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="navigation.jsp">
                <jsp:param name="showAsLink" value="false"/>
            </jsp:include>
        <%@ include file="../include/breadcrumb/breadcrumbs-end.jsp" %>
        <et:hasSecurityRole securityRole="Company.List">
            <a href="<c:url value="/action/Accounting/Company/Main" />">Companies</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="GlAccount.List">
            <a href="<c:url value="/action/Accounting/GlAccount/Main" />">Gl Accounts</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="GlAccountCategory.List">
            <a href="<c:url value="/action/Accounting/GlAccountCategory/Main" />">Gl Account Categories</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="GlAccountClass.List">
            <a href="<c:url value="/action/Accounting/GlAccountClass/Main" />">Gl Account Classes</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="GlResourceType.List">
            <a href="<c:url value="/action/Accounting/GlResourceType/Main" />">Gl Resource Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Tax.List">
            <a href="<c:url value="/action/Accounting/Tax/Main" />">Taxes</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Term.List">
            <a href="<c:url value="/action/Accounting/Term/Main" />">Terms</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="ItemAccountingCategory.List">
            <a href="<c:url value="/action/Accounting/ItemAccountingCategory/Main" />">Item Accounting Categories</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="TransactionType.List">
            <a href="<c:url value="/action/Accounting/TransactionType/Main" />">Transaction Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="TransactionGroup.List">
            <a href="<c:url value="/action/Accounting/TransactionGroup/Main" />">Transaction Groups</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="Currency.List">
            <a href="<c:url value="/action/Accounting/Currency/Main" />">Currencies</a><br />
        </et:hasSecurityRole>
    <%@ include file="../include/body-end-b.jsp" %>
</html>
