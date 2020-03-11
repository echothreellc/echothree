<div class="form-group form-row">
    <label for="firstName" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;">
        <fmt:message key="label.firstName" />:
    </label>
    <div class="col-sm-3">
        <html:text styleClass="form-control form-control-sm" styleId="firstName" property="firstName" size="20" maxlength="20" />
        <c:if test="${showSoundex == 'true'}">
            <label for="firstNameSoundex" class="col-form-label col-form-label-sm" style="text-align: right;">
                <fmt:message key="label.soundex" />:
            </label>
            <html:checkbox property="firstNameSoundex" value="true" />
        </c:if>
        <et:validationErrors id="errorMessage" property="FirstName">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
        <c:if test="${showSoundex == 'true'}">
            <et:validationErrors id="errorMessage" property="FirstNameSoundex">
                <br/>
                <div class="alert alert-danger" role="alert">
                    <c:out value="${errorMessage}" />
                </div>
            </et:validationErrors>
        </c:if>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
