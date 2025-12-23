<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<!--                                                                                  -->
<!-- Copyright 2002-2026 Echo Three, LLC                                              -->
<!--                                                                                  -->
<!-- Licensed under the Apache License, Version 2.0 (the "License");                  -->
<!-- you may not use this file except in compliance with the License.                 -->
<!-- You may obtain a copy of the License at                                          -->
<!--                                                                                  -->
<!--     http://www.apache.org/licenses/LICENSE-2.0                                   -->
<!--                                                                                  -->
<!-- Unless required by applicable law or agreed to in writing, software              -->
<!-- distributed under the License is distributed on an "AS IS" BASIS,                -->
<!-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.         -->
<!-- See the License for the specific language governing permissions and              -->
<!-- limitations under the License.                                                   -->
<!--                                                                                  -->

<%@ include file="../include/taglibs.jsp" %>

<html>
    <head>
        <title>Employee Login</title>
        <%@ include file="../include/environment-b.jsp" %>
    </head>
    <body>
        <header>
            <nav class="navbar navbar-dark bg-dark fixed-top">
                <a class="navbar-brand">Echo Three</a>
            </nav>
        </header>
        <main role="main" class="container">
            <div class="card mx-auto">
                <div class="card-body">
                    <%@ include file="../include/breadcrumb/breadcrumbs-start.jsp" %>
                        <li class="breadcrumb-item active" aria-current="page"><fmt:message key="navigation.employeeLogin" /></li>
                    <%@ include file="../include/breadcrumb/breadcrumbs-end.jsp" %>
                    <et:containsExecutionError key="EmployeeLogin.IncorrectPassword">
                        <div class="alert alert-danger" role="alert">
                            Incorrect password.
                        </div>
                    </et:containsExecutionError>
                    <et:executionErrors id="errorMessage">
                        <div class="alert alert-danger" role="alert">
                            <c:out value="${errorMessage}" />
                        </div>
                    </et:executionErrors>
                    <html:form action="/Employee/Login" method="POST">
                        <c:set var="requiredField" value="true"/>
                        <%@ include file="../include/field/username-b.jsp" %>
                        <%@ include file="../include/field/password-b.jsp" %>
                        <%@ include file="../include/field/companyChoice-b.jsp" %>
                        <%@ include file="../include/field/submit-b.jsp" %>
                        <html:hidden property="returnUrl" />
                    </html:form>
                    <script type="text/javascript">
                        const form = document.forms["EmployeeLogin"];
                        let focusControl = form.elements["username"];

                        if(focusControl.value.length > 0) {
                            focusControl = form.elements["password"];
                        }

                        focusControl.focus();
                    </script>
                </div>
            </div>
            <%@ include file="../include/copyright-b.jsp" %>
        </main>
    </body>
<html>