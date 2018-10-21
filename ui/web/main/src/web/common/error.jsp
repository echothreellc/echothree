<%@ include file="../include/taglibs.jsp" %>

<html:html xhtml="true">
    <head>
        <title>Error</title>
        <html:base/>
        <link rel="stylesheet" href="../stylesheets/bluerobot.css" type="text/css">
    </head>
    <body>
        <div id="Header">
            <h2>Error</h2>
        </div>
        <div id="Content">
            <p class="executionErrors">An error has occurred. Please include all the information from this page when reporting the problem.</p>
            <br />
            <p>Request URI: <c:out value="${requestScope['javax.servlet.forward.request_uri']}" /></p>
            <c:if test="${pageContext.request.queryString != null}">
                <p>Query string: <c:out value="${pageContext.request.queryString}" /></p>
            </c:if>
            <fmt:setBundle basename="echothree-build" var="buildProperties" />
            <p>Subversion information:</p>
            <ul>
                <li>URL: <fmt:message key="subversion.url" bundle="${buildProperties}" /></li>
                <li>Revision: <fmt:message key="subversion.revision" bundle="${buildProperties}" /></li>
            </ul>
            <p>Build:</p>
            <ul>
                <li>Time: <fmt:message key="build.time" bundle="${buildProperties}" /></li>
                <li>User: <fmt:message key="build.user" bundle="${buildProperties}" /></li>
                <li>Instance: <fmt:message key="build.instance" bundle="${buildProperties}" /></li>
            </ul>
            <p>Page context:</p>
            <ul>
                <li>Character encoding: <c:out value='${pageContext.request.characterEncoding}'/></li>
                <li>Content type: <c:out value='${pageContext.request.contentType}'/></li>
                <li>Protocol: <c:out value='${pageContext.request.protocol}'/></li>
                <li>Remote address: <c:out value='${pageContext.request.remoteAddr}'/></li>
                <li>Remote host: <c:out value='${pageContext.request.remoteHost}'/></li>
                <li>Scheme: <c:out value='${pageContext.request.scheme}'/></li>
                <li>Server name: <c:out value='${pageContext.request.serverName}'/></li>
                <li>Server port: <c:out value='${pageContext.request.serverPort}'/></li>
                <li>Secure: <c:out value='${pageContext.request.secure}'/></li>
            </ul>
            <p>Session:</p>
            <ul>
                <li>Id: <c:out value='${pageContext.session.id}'/></li>
                <li>Creation time: <c:out value='${pageContext.session.creationTime}'/></li>
                <li>Last accessed time: <c:out value='${pageContext.session.lastAccessedTime}'/></li>
                <li>Active for: <c:out value='${pageContext.session.lastAccessedTime - pageContext.session.creationTime}'/></li>
                <li>Maximum inactive interval: <c:out value='${pageContext.session.maxInactiveInterval}'/></li>
            </ul>
            <p>Request headers:</p>
            <ul>
                <c:forEach items='${header}' var='h'>
                    <c:if test="${h.key != 'cookie'}">
                        <li><c:out value='${h.key}'/>: <c:out value='${h.value}'/></li>
                    </c:if>
                </c:forEach>
            </ul>
            <p>Cookies:</p>
            <ul>
                <c:forEach items='${cookie}' var='c'>
                    <li><c:out value='${c.key}'/>: <c:out value='${c.value.value}'/></li>
                </c:forEach>
            </ul>
            <c:choose>
                <c:when test="${stackTrace == null}">
                    No stack trace was found.
                </c:when>
                <c:otherwise>
                    <p>Stack trace:</p>
                    <pre><c:out value="${stackTrace}" /></pre>
                </c:otherwise>
            </c:choose>
        </div>
        <jsp:include page="../include/copyright.jsp" />
    </body>
</html:html>
