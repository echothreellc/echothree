<div class="form-group form-row">
    <label for="lastName" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;"><fmt:message key="label.lastName" />:</label>
    <div class="col-sm-5">
        <html:text styleClass="form-control form-control-sm" styleId="lastName" property="lastName" size="20" maxlength="20" />
        <et:validationErrors id="errorMessage" property="LastName">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
