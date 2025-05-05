<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="admin-sidebar">
    <div class="sidebar-header">
        <h3>Admin Panel</h3>
    </div>
    <ul class="sidebar-menu">
        <li class="${param.active == 'dashboard' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/admin/dashboard">
                <i class="fa fa-dashboard"></i> Dashboard
            </a>
        </li>
        <li class="${param.active == 'users' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/admin/users">
                <i class="fa fa-users"></i> Users
            </a>
        </li>
        <li class="${param.active == 'movies' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/admin/movies">
                <i class="fa fa-film"></i> Movies
            </a>
        </li>
        <li class="${param.active == 'shows' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/admin/shows">
                <i class="fa fa-calendar"></i> Shows
            </a>
        </li>
        <li class="${param.active == 'bookings' ? 'active' : ''}">
            <a href="${pageContext.request.contextPath}/admin/bookings">
                <i class="fa fa-ticket"></i> Bookings
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/">
                <i class="fa fa-home"></i> Back to Site
            </a>
        </li>
    </ul>
</div>
