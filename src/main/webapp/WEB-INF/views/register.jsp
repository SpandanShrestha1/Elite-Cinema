<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Register" />
</jsp:include>

<div class="container">
    <div class="form-container">
        <div class="form-title">
            <h2>Create an Account</h2>
        </div>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" value="${name}" required>
                <c:if test="${not empty nameError}">
                    <div class="error">${nameError}</div>
                </c:if>
            </div>
            
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" value="${email}" required>
                <c:if test="${not empty emailError}">
                    <div class="error">${emailError}</div>
                </c:if>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
                <c:if test="${not empty passwordError}">
                    <div class="error">${passwordError}</div>
                </c:if>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <c:if test="${not empty confirmPasswordError}">
                    <div class="error">${confirmPasswordError}</div>
                </c:if>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Register</button>
            </div>
        </form>
        
        <div class="form-footer">
            <p>Already have an account? <a href="${pageContext.request.contextPath}/login">Login here</a></p>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
