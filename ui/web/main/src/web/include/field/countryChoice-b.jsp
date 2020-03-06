<div class="form-group form-row">
    <label for="countryChoice" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;"><fmt:message key="label.country" />:</label>
    <div class="col-sm-3">
        <html:select styleClass="form-control form-control-sm" styleId="countryChoice" property="countryChoice">
            <html:optionsCollection property="countryChoices" />
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
