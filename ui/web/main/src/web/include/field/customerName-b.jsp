<div class="form-group form-row">
    <label for="customerName" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;"><fmt:message key="label.customerName" />:</label>
    <div class="col-sm-5">
        <html:text styleClass="form-control form-control-sm" styleId="customerName" property="customerName" size="20" maxlength="40" />
        <et:validationErrors id="errorMessage" property="CustomerName">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
