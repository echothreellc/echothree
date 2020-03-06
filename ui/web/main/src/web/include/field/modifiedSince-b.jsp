<div class="form-group form-row">
    <label for="modifiedSince" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;">
        <fmt:message key="label.modifiedSince" />:
    </label>
    <div class="col-sm-5">
        <html:text styleClass="form-control form-control-sm" styleId="modifiedSince" property="modifiedSince" size="60" maxlength="30" />
        <et:validationErrors id="errorMessage" property="ModifiedSince">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
