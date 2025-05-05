<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Booking Confirmation" />
</jsp:include>

<div class="container">
    <div class="booking-confirmation">
        <div class="confirmation-icon">
            <i class="fa fa-check-circle"></i>
        </div>
        <h2>Booking Confirmed!</h2>
        <p>Your booking has been successfully confirmed. Your booking ID is <strong>${param.bookingId}</strong>.</p>
        <p>A confirmation email has been sent to your registered email address.</p>
        
        <div class="booking-details">
            <h3>Booking Details</h3>
            <table>
                <tr>
                    <th>Booking ID</th>
                    <td>${param.bookingId}</td>
                </tr>
                <tr>
                    <th>Movie</th>
                    <td>${booking.show.movie.title}</td>
                </tr>
                <tr>
                    <th>Date</th>
                    <td><fmt:formatDate value="${booking.show.date}" pattern="EEE, MMM dd, yyyy" /></td>
                </tr>
                <tr>
                    <th>Time</th>
                    <td><fmt:formatDate value="${booking.show.time}" pattern="hh:mm a" /></td>
                </tr>
                <tr>
                    <th>Seats</th>
                    <td>${booking.seatNumbers}</td>
                </tr>
                <tr>
                    <th>Total Amount</th>
                    <td>$<fmt:formatNumber value="${booking.totalAmount}" pattern="#,##0.00" /></td>
                </tr>
            </table>
        </div>
        
        <div class="confirmation-actions">
            <a href="${pageContext.request.contextPath}/user/bookings" class="btn btn-primary">View My Bookings</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Back to Home</a>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
