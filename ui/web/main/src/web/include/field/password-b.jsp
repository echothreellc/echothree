<div class="form-group form-row">
    <label for="password" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;"><fmt:message key="label.password" />:</label>
    <div class="col-sm-3">
        <html:password styleClass="form-control form-control-sm" styleId="password" property="password" size="20" maxlength="40" />
        <et:validationErrors id="errorMessage" property="Password">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
