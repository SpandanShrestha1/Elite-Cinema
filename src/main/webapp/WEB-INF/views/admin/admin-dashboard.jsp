<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Admin Dashboard" />
    <jsp:param name="isAdmin" value="true" />
</jsp:include>

<div class="admin-container">
    <jsp:include page="../common/admin-sidebar.jsp">
        <jsp:param name="active" value="dashboard" />
    </jsp:include>

    <div class="admin-content">
        <div class="admin-header">
            <h2>Dashboard</h2>
        </div>

        <div class="dashboard-stats">
            <div class="stat-card">
                <h3>Total Users</h3>
                <div class="stat-value">${userCount}</div>
            </div>
            <div class="stat-card">
                <h3>Total Movies</h3>
                <div class="stat-value">${movieCount}</div>
            </div>
            <div class="stat-card">
                <h3>Total Shows</h3>
                <div class="stat-value">${showCount}</div>
            </div>
            <div class="stat-card">
                <h3>Total Bookings</h3>
                <div class="stat-value">${bookingCount}</div>
            </div>
            <div class="stat-card">
                <h3>Total Revenue</h3>
                <div class="stat-value">Rs. <fmt:formatNumber value="${totalRevenue}" pattern="#,##0.00" /></div>
            </div>
        </div>

        <div class="recent-bookings">
            <h3>Recent Bookings</h3>
            <c:choose>
                <c:when test="${empty recentBookings}">
                    <p>No bookings available.</p>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>Booking ID</th>
                                <th>User</th>
                                <th>Movie</th>
                                <th>Date & Time</th>
                                <th>Seats</th>
                                <th>Amount</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="booking" items="${recentBookings}">
                                <tr>
                                    <td>${booking.bookingId}</td>
                                    <td>${booking.user.name}</td>
                                    <td>${booking.show.movie.title}</td>
                                    <td>
                                        <fmt:formatDate value="${booking.show.date}" pattern="MMM dd, yyyy" /> at
                                        <fmt:formatDate value="${booking.show.time}" pattern="hh:mm a" />
                                    </td>
                                    <td>${booking.seatNumbers}</td>
                                    <td>$<fmt:formatNumber value="${booking.totalAmount}" pattern="#,##0.00" /></td>
                                    <td>
                                        <span class="status ${booking.status.toLowerCase()}">${booking.status}</span>
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
