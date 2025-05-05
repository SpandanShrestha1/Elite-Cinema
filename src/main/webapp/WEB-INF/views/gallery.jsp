<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Gallery" />
</jsp:include>

<div class="container">
    <div class="section-title">
        <h2>Movie Gallery</h2>
    </div>
    
    <div class="gallery">
        <div class="gallery-grid">
            <c:choose>
                <c:when test="${empty movies}">
                    <div class="no-movies">
                        <p>No images available in the gallery.</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="movie" items="${movies}">
                        <div class="gallery-item">
                            <c:choose>
                                <c:when test="${not empty movie.imagePath}">
                                    <a href="${pageContext.request.contextPath}/movie/${movie.movieId}">
                                        <img src="${pageContext.request.contextPath}/${movie.imagePath}" alt="${movie.title}">
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/movie/${movie.movieId}">
                                        <img src="${pageContext.request.contextPath}/images/no-poster.jpg" alt="No Poster Available">
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
