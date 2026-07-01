<div class="row mb-3">
    <label for="companyChoice" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;"><fmt:message key="label.company" />:</label>
    <div class="col-sm-3">
        <html:select styleClass="form-select form-select-sm" styleId="companyChoice" property="companyChoice">
            <html:optionsCollection property="companyChoices" />
        </html:select>
        <et:validationErrors id="errorMessage" property="CompanyName">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
