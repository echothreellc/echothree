<div class="form-group form-row">
    <label for="username" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;"><fmt:message key="label.username" />:</label>
    <div class="col-sm-5">
        <html:text styleClass="form-control form-control-sm" styleId="username" property="username" size="40" maxlength="80" />
        <et:validationErrors id="errorMessage" property="Username">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
