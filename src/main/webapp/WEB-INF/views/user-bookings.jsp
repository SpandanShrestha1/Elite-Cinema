<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="My Bookings" />
</jsp:include>

<div class="container">
    <div class="user-bookings">
        <div class="section-title">
            <h2>My Bookings</h2>
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
        
        <c:choose>
            <c:when test="${empty bookings}">
                <div class="no-bookings">
                    <p>You don't have any bookings yet.</p>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Browse Movies</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="booking-list">
                    <table>
                        <thead>
                            <tr>
                                <th>Booking ID</th>
                                <th>Movie</th>
                                <th>Date & Time</th>
                                <th>Seats</th>
                                <th>Amount</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="booking" items="${bookings}">
                                <tr>
                                    <td>${booking.bookingId}</td>
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
                                    <td class="actions">
                                        <c:if test="${booking.status == 'CONFIRMED'}">
                                            <a href="${pageContext.request.contextPath}/user/booking/cancel?id=${booking.bookingId}" 
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
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
