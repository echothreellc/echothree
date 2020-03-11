<div class="form-group form-row">
    <label for="createdSince" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;">
        <fmt:message key="label.createdSince" />:</label>

    <div class="col-sm-4">
        <html:text styleClass="form-control form-control-sm" styleId="createdSince" property="createdSince" size="60" maxlength="30" />
        <et:validationErrors id="errorMessage" property="CreatedSince">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
