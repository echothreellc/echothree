<div class="form-group form-row">
    <label for="lastName" class="col-sm-2 col-form-label col-form-label-sm" style="text-align: right;">
        <fmt:message key="label.lastName" />:
    </label>
    <div class="col-sm-5">
        <html:text styleClass="form-control form-control-sm" styleId="lastName" property="lastName" size="20" maxlength="20" />
        <c:if test="${showSoundex == 'true'}">
            <label for="lastNameSoundex" class="col-form-label col-form-label-sm" style="text-align: right;">
                <fmt:message key="label.soundex" />:
            </label>
            <html:checkbox property="lastNameSoundex" value="true" />
        </c:if>
        <et:validationErrors id="errorMessage" property="LastName">
            <br/>
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" />
            </div>
        </et:validationErrors>
        <c:if test="${showSoundex == 'true'}">
            <et:validationErrors id="errorMessage" property="LastNameSoundex">
                <br/>
                <div class="alert alert-danger" role="alert">
                    <c:out value="${errorMessage}" />
                </div>
            </et:validationErrors>
        </c:if>
    </div>
    <%@ include file="requiredField-b.jsp" %>
</div>
