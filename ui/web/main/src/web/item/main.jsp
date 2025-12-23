<!DOCTYPE html>
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

<%@ include file="../include/taglibs.jsp" %>

<html>
    <head>
        <title><fmt:message key="navigation.items" /></title>
        <%@ include file="../include/environment-b.jsp" %>
    </head>
    <%@ include file="../include/body-start-b.jsp" %>
        <et:checkSecurityRoles securityRoles="Item.List:ItemCategory.List:ItemAliasType.List:ItemWeightType.List:ItemVolumeType.List:ItemDescriptionType.List:ItemDescriptionTypeUseType.List:ItemImageType.List:RelatedItemType.List:HarmonizedTariffScheduleCodeUseType.List:HarmonizedTariffScheduleCodeUnit.List" />
        <%@ include file="../include/breadcrumb/breadcrumbs-start.jsp" %>
            <jsp:include page="../include/breadcrumb/portal.jsp">
                <jsp:param name="showAsLink" value="true"/>
            </jsp:include>
            <jsp:include page="navigation.jsp">
                <jsp:param name="showAsLink" value="false"/>
            </jsp:include>
        <%@ include file="../include/breadcrumb/breadcrumbs-end.jsp" %>
        <et:hasSecurityRole securityRole="Item.List">
            <a href="<c:url value="/action/Item/Item/Main" />">Items</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="ItemCategory.List">
            <a href="<c:url value="/action/Item/ItemCategory/Main" />">Item Categories</a><br />
        </et:hasSecurityRole>
    <et:hasSecurityRole securityRole="ItemAliasType.List">
        <a href="<c:url value="/action/Item/ItemAliasType/Main" />">Item Alias Types</a><br />
    </et:hasSecurityRole>
    <et:hasSecurityRole securityRole="ItemWeightType.List">
        <a href="<c:url value="/action/Item/ItemWeightType/Main" />">Item Weight Types</a><br />
    </et:hasSecurityRole>
    <et:hasSecurityRole securityRole="ItemVolumeType.List">
        <a href="<c:url value="/action/Item/ItemVolumeType/Main" />">Item Volume Types</a><br />
    </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="ItemDescriptionType.List">
            <a href="<c:url value="/action/Item/ItemDescriptionType/Main" />">Item Description Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="ItemDescriptionTypeUseType.List">
            <a href="<c:url value="/action/Item/ItemDescriptionTypeUseType/Main" />">Item Description Type Use Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="ItemImageType.List">
            <a href="<c:url value="/action/Item/ItemImageType/Main" />">Item Image Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="RelatedItemType.List">
            <a href="<c:url value="/action/Item/RelatedItemType/Main" />">Related Item Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUseType.List">
            <a href="<c:url value="/action/Item/HarmonizedTariffScheduleCodeUseType/Main" />">Harmonized Tariff Schedule Code Use Types</a><br />
        </et:hasSecurityRole>
        <et:hasSecurityRole securityRole="HarmonizedTariffScheduleCodeUnit.List">
            <a href="<c:url value="/action/Item/HarmonizedTariffScheduleCodeUnit/Main" />">Harmonized Tariff Schedule Code Units</a><br />
        </et:hasSecurityRole>
    <%@ include file="../include/body-end-b.jsp" %>
</html>
