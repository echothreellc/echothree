<div class="row mb-3">
    <label for="customerAliasType" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;">
        <fmt:message key="label.customerAliasType" />:
    </label>
    <div class="col-sm-3">
        <html:select styleClass="form-select form-select-sm" styleId="partyAliasTypeChoice" property="partyAliasTypeChoice">
            <html:optionsCollection property="partyAliasTypeChoices" />
        </html:select>
        <et:validationErrors id="errorMessage" property="PartyAliasTypeName">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
