<div class="form-group form-row">
    <label for="emailAddress" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;"><fmt:message key="label.emailAddress" />:</label>
    <div class="col-sm-8">
        <html:text styleClass="form-control form-control-sm" styleId="emailAddress" property="emailAddress" size="40" maxlength="80" />
        <et:validationErrors id="errorMessage" property="EmailAddress">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
