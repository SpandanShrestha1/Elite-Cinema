<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title} - Elite Cinema</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <c:if test="${param.isAdmin}">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    </c:if>
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <!-- SweetAlert2 for better alerts -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
    <header>
        <div class="navbar">
            <div class="logo">
                <a href="${pageContext.request.contextPath}/">
                    <img src="${pageContext.request.contextPath}/images/logo.png" alt="Elite Cinema">
                </a>
            </div>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                    <li><a href="${pageContext.request.contextPath}/gallery">Gallery</a></li>
                    <li><a href="${pageContext.request.contextPath}/ticket-rate">Ticket Rate</a></li>
                </ul>
            </nav>
            <div class="auth-buttons">
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Login</a>
                        <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary">Register</a>
                    </c:when>
                    <c:otherwise>
                        <div class="user-menu">
                            <span>Welcome, ${sessionScope.user.name}</span>
                            <div class="dropdown-content">
                                <c:if test="${sessionScope.user.admin}">
                                    <a href="${pageContext.request.contextPath}/admin/dashboard">Admin Dashboard</a>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/user/bookings">My Bookings</a>
                                <a href="${pageContext.request.contextPath}/logout">Logout</a>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </header>
    <div class="original-site-link">
        <a href="https://elitecinemanepal.com" target="_blank">Back to Original Site</a>
    </div>