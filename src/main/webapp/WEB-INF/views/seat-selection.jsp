<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Select Seats" />
    <jsp:param name="customJS" value="seat-selection.js" />
</jsp:include>

<!-- Add direct script reference to ensure it loads -->
<script src="${pageContext.request.contextPath}/js/seat-selection.js"></script>

<div class="container">
    <div class="seat-selection">
        <div class="section-title">
            <h2>Select Your Seats</h2>
            <h3>${show.movie.title} - <fmt:formatDate value="${show.date}" pattern="EEE, MMM dd" /> at <fmt:formatDate value="${show.time}" pattern="hh:mm a" /></h3>
        </div>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">
                ${param.error}
            </div>
        </c:if>

        <div class="screen">
            <p class="screen-label">SCREEN</p>
        </div>

        <div class="seat-legend">
            <div class="legend-item">
                <div class="legend-box available"></div>
                <span>Available</span>
            </div>
            <div class="legend-item">
                <div class="legend-box selected"></div>
                <span>Selected</span>
            </div>
            <div class="legend-item">
                <div class="legend-box booked"></div>
                <span>Booked</span>
            </div>
        </div>

        <div class="seat-map">
            <c:set var="totalSeats" value="${show.totalSeats}" />
            <c:set var="availableSeats" value="${show.availableSeats}" />
            <c:set var="bookedSeats" value="${totalSeats - availableSeats}" />

            <c:forEach var="i" begin="1" end="${totalSeats}">
                <c:choose>
                    <c:when test="${i <= bookedSeats}">
                        <div class="seat booked" data-seat="${i}">${i}</div>
                    </c:when>
                    <c:otherwise>
                        <div class="seat available" data-seat="${i}">${i}</div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <div class="seat-selection-summary">
            <p>Selected Seats: <span id="selectedSeats"></span></p>
            <p>Price per Seat: Rs. <fmt:formatNumber value="${show.price}" pattern="#,##0.00" /></p>
            <p>Total Price: Rs. <span id="totalPrice" data-price="${show.price}">0.00</span></p>
        </div>

        <form id="seatSelectionForm" action="${pageContext.request.contextPath}/booking/payment" method="post">
            <input type="hidden" name="showId" value="${show.showId}">
            <div class="form-actions">
                <button id="continueButton" type="submit" class="btn btn-primary" disabled>Continue to Payment</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
