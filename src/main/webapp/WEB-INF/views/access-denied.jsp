<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Access Denied" />
</jsp:include>

<div class="container">
    <div class="access-denied">
        <div class="error-icon">
            <i class="fa fa-exclamation-triangle"></i>
        </div>
        <h2>Access Denied</h2>
        <p>Sorry, you don't have permission to access this page.</p>
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Go to Home</a>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
