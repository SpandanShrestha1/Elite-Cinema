<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Admin - Banners" />
    <jsp:param name="isAdmin" value="true" />
</jsp:include>

<div class="admin-container">
    <jsp:include page="../common/admin-sidebar.jsp">
        <jsp:param name="active" value="banners" />
    </jsp:include>
    
    <div class="admin-content">
        <div class="admin-header">
            <h2>Banner Management</h2>
            <a href="${pageContext.request.contextPath}/admin/banner/add" class="btn btn-primary">Add New Banner</a>
        </div>
        
        <c:if test="${not empty param.message}">
            <div class="alert alert-success">
                ${param.message}
            </div>
        </c:if>
        
        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">
                ${param.error}
            </div>
        </c:if>
        
        <div class="admin-table">
            <c:choose>
                <c:when test="${empty banners}">
                    <p>No banners available.</p>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Image</th>
                                <th>Title</th>
                                <th>Description</th>
                                <th>Status</th>
                                <th>Created At</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="banner" items="${banners}">
                                <tr>
                                    <td>${banner.bannerId}</td>
                                    <td class="banner-thumbnail">
                                        <img src="${pageContext.request.contextPath}/${banner.imagePath}" alt="${banner.title}">
                                    </td>
                                    <td>${banner.title}</td>
                                    <td>${banner.description}</td>
                                    <td>
                                        <span class="status ${banner.active ? 'active' : 'inactive'}">
                                            ${banner.active ? 'Active' : 'Inactive'}
                                        </span>
                                    </td>
                                    <td><fmt:formatDate value="${banner.createdAt}" pattern="MMM dd, yyyy HH:mm" /></td>
                                    <td class="actions">
                                        <a href="${pageContext.request.contextPath}/admin/banner/edit?id=${banner.bannerId}" class="btn btn-primary">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/banner/toggle?id=${banner.bannerId}" 
                                           class="btn ${banner.active ? 'btn-warning' : 'btn-success'}"
                                           onclick="return confirm('Are you sure you want to ${banner.active ? 'deactivate' : 'activate'} this banner?')">
                                            ${banner.active ? 'Deactivate' : 'Activate'}
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/banner/delete?id=${banner.bannerId}" 
                                           class="btn btn-danger"
                                           onclick="return confirm('Are you sure you want to delete this banner? This action cannot be undone.')">
                                            Delete
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
