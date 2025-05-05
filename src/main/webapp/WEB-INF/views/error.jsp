<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Error" />
</jsp:include>

<div class="container">
    <div class="error-page">
        <div class="error-icon">
            <i class="fa fa-exclamation-circle"></i>
        </div>
        <h2>Oops! Something went wrong</h2>
        <p>We're sorry, but an error occurred while processing your request.</p>
        <p>Error code: ${pageContext.errorData.statusCode}</p>
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Go to Home</a>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
