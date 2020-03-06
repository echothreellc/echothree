<div class="form-group form-row">
    <label for="alias" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;"><fmt:message key="label.alias" />:</label>
    <div class="col-sm-5">
        <html:text styleClass="form-control form-control-sm" styleId="alias" property="alias" size="20" maxlength="40" />
        <et:validationErrors id="errorMessage" property="Alias">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
