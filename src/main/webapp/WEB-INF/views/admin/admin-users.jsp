<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Admin - Users" />
    <jsp:param name="isAdmin" value="true" />
</jsp:include>

<div class="admin-container">
    <jsp:include page="../common/admin-sidebar.jsp">
        <jsp:param name="active" value="users" />
    </jsp:include>
    
    <div class="admin-content">
        <div class="admin-header">
            <h2>User Management</h2>
        </div>
        
        <div class="admin-table">
            <c:choose>
                <c:when test="${empty users}">
                    <p>No users available.</p>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Created At</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.name}</td>
                                    <td>${user.email}</td>
                                    <td>
                                        <span class="status ${user.admin ? 'active' : 'inactive'}">
                                            ${user.admin ? 'Admin' : 'User'}
                                        </span>
                                    </td>
                                    <td><fmt:formatDate value="${user.createdAt}" pattern="MMM dd, yyyy HH:mm" /></td>
                                    <td class="actions">
                                        <a href="${pageContext.request.contextPath}/admin/user/toggleAdmin?id=${user.userId}" 
                                           class="btn ${user.admin ? 'btn-danger' : 'btn-primary'}"
                                           onclick="return confirm('Are you sure you want to ${user.admin ? 'remove admin rights from' : 'make admin'} this user?')">
                                            ${user.admin ? 'Remove Admin' : 'Make Admin'}
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/user/delete?id=${user.userId}" 
                                           class="btn btn-danger"
                                           onclick="return confirm('Are you sure you want to delete this user? This action cannot be undone.')">
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
