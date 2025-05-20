<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Admin - Bookings" />
    <jsp:param name="isAdmin" value="true" />
</jsp:include>

<div class="admin-container">
    <jsp:include page="../common/admin-sidebar.jsp">
        <jsp:param name="active" value="bookings" />
    </jsp:include>

    <div class="admin-content">
        <div class="admin-header">
            <h2>Booking Management</h2>
        </div>

        <div class="admin-table">
            <c:choose>
                <c:when test="${empty bookings}">
                    <p>No bookings available.</p>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>User</th>
                                <th>Movie</th>
                                <th>Date & Time</th>
                                <th>Seats</th>
                                <th>Amount</th>
                                <th>Booking Date</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="booking" items="${bookings}">
                                <tr>
                                    <td>${booking.bookingId}</td>
                                    <td>${booking.user.name}</td>
                                    <td>${booking.show.movie.title}</td>
                                    <td>
                                        <fmt:formatDate value="${booking.show.date}" pattern="MMM dd, yyyy" /> at
                                        <fmt:formatDate value="${booking.show.time}" pattern="hh:mm a" />
                                    </td>
                                    <td>${booking.seatNumbers}</td>
                                    <td>Rs. <fmt:formatNumber value="${booking.totalAmount}" pattern="#,##0.00" /></td>
                                    <td><fmt:formatDate value="${booking.bookingDate}" pattern="MMM dd, yyyy HH:mm" /></td>
                                    <td>
                                        <span class="status ${booking.status.toLowerCase()}">${booking.status}</span>
                                    </td>
                                    <td class="actions">
                                        <c:if test="${booking.status == 'CONFIRMED'}">
                                            <a href="${pageContext.request.contextPath}/admin/booking/cancel?id=${booking.bookingId}"
                                               class="btn btn-danger"
                                               onclick="return confirm('Are you sure you want to cancel this booking?')">
                                                Cancel
                                            </a>
                                        </c:if>
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
