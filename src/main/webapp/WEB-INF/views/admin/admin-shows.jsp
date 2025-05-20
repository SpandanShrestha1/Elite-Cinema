<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Admin - Shows" />
    <jsp:param name="isAdmin" value="true" />
</jsp:include>

<div class="admin-container">
    <jsp:include page="../common/admin-sidebar.jsp">
        <jsp:param name="active" value="shows" />
    </jsp:include>

    <div class="admin-content">
        <div class="admin-header">
            <h2>Show Management</h2>
            <a href="${pageContext.request.contextPath}/admin/show/add" class="btn btn-primary">Add New Show</a>
        </div>

        <div class="admin-table">
            <c:choose>
                <c:when test="${empty shows}">
                    <p>No shows available.</p>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Movie</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Total Seats</th>
                                <th>Available Seats</th>
                                <th>Price</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="show" items="${shows}">
                                <tr>
                                    <td>${show.showId}</td>
                                    <td>${show.movie.title}</td>
                                    <td><fmt:formatDate value="${show.date}" pattern="MMM dd, yyyy" /></td>
                                    <td><fmt:formatDate value="${show.time}" pattern="hh:mm a" /></td>
                                    <td>${show.totalSeats}</td>
                                    <td>${show.availableSeats}</td>
                                    <td>Rs. <fmt:formatNumber value="${show.price}" pattern="#,##0.00" /></td>
                                    <td class="actions">
                                        <a href="${pageContext.request.contextPath}/admin/show/edit?id=${show.showId}" class="btn btn-primary">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/show/delete?id=${show.showId}"
                                           class="btn btn-danger"
                                           onclick="return confirm('Are you sure you want to delete this show? This action cannot be undone.')">
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
