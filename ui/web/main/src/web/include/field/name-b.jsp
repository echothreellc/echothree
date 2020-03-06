<div class="form-group form-row">
    <label for="name" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;"><fmt:message key="label.company" />:</label>
    <div class="col-sm-5">
        <html:text styleClass="form-control form-control-sm" styleId="name" property="name" size="40" maxlength="40" />
        <et:validationErrors id="errorMessage" property="Name">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
