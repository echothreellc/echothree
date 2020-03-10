<div class="form-group form-row">
    <div class="col-sm-2">
    </div>
    <div class="col-sm-1">
        <html:text styleClass="form-control form-control-sm" styleId="areaCode" property="areaCode" size="5" maxlength="5" />
        <label for="areaCode" class="col-form-label col-form-label-sm"><fmt:message key="label.areaCode" /></label>
        <et:validationErrors id="errorMessage" property="AreaCode">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <div class="col-sm-3">
        <html:text styleClass="form-control form-control-sm" styleId="telephoneNumber" property="telephoneNumber" size="15" maxlength="25" />
        <label for="telephoneNumber" class="col-form-label col-form-label-sm"><fmt:message key="label.telephoneNumber" /></label>
        <et:validationErrors id="errorMessage" property="TelephoneNumber">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <div class="col-sm-2">
        <html:text styleClass="form-control form-control-sm" styleId="telephoneExtension" property="telephoneExtension" size="10" maxlength="10" />
        <label for="telephoneExtension" class="col-form-label col-form-label-sm"><fmt:message key="label.telephoneExtension" /></label>
        <et:validationErrors id="errorMessage" property="TelephoneExtension">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
