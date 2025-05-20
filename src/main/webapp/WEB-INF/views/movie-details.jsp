<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="${movie.title}" />
</jsp:include>

<div class="container">
    <div class="movie-details">
        <div class="movie-poster-large">
            <c:choose>
                <c:when test="${not empty movie.imagePath}">
                    <img src="${pageContext.request.contextPath}/${movie.imagePath}" alt="${movie.title}">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/no-poster.jpg" alt="No Poster Available">
                </c:otherwise>
            </c:choose>
        </div>
        <div class="movie-details-info">
            <h2>${movie.title}</h2>
            <div class="movie-meta">
                <span>${movie.genre}</span>
                <span>${movie.duration} min</span>
                <span><fmt:formatDate value="${movie.releaseDate}" pattern="MMM dd, yyyy" /></span>
            </div>
            <p>${movie.description}</p>

            <div class="showtimes">
                <h3>Showtimes</h3>
                <c:choose>
                    <c:when test="${empty shows}">
                        <p>No showtimes available for this movie.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="showtime-list">
                            <c:forEach var="show" items="${shows}">
                                <div class="showtime-item">
                                    <div class="showtime-date">
                                        <fmt:formatDate value="${show.date}" pattern="EEE, MMM dd" />
                                    </div>
                                    <div class="showtime-time">
                                        <fmt:formatDate value="${show.time}" pattern="hh:mm a" />
                                    </div>
                                    <div class="showtime-price">
                                        Rs. <fmt:formatNumber value="${show.price}" pattern="#,##0.00" />
                                    </div>
                                    <div class="showtime-seats">
                                        ${show.availableSeats} seats available
                                    </div>
                                    <a href="${pageContext.request.contextPath}/booking/seats?showId=${show.showId}" class="btn btn-primary">Book Now</a>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
