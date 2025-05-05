<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>

<div class="container">
    <div class="section-title">
        <h2>Now Showing</h2>
    </div>
    
    <div class="genre-filter">
        <form action="${pageContext.request.contextPath}/" method="get">
            <select name="genre" onchange="this.form.submit()">
                <option value="">All Genres</option>
                <option value="Action" ${selectedGenre == 'Action' ? 'selected' : ''}>Action</option>
                <option value="Comedy" ${selectedGenre == 'Comedy' ? 'selected' : ''}>Comedy</option>
                <option value="Drama" ${selectedGenre == 'Drama' ? 'selected' : ''}>Drama</option>
                <option value="Horror" ${selectedGenre == 'Horror' ? 'selected' : ''}>Horror</option>
                <option value="Sci-Fi" ${selectedGenre == 'Sci-Fi' ? 'selected' : ''}>Sci-Fi</option>
                <option value="Romance" ${selectedGenre == 'Romance' ? 'selected' : ''}>Romance</option>
                <option value="Thriller" ${selectedGenre == 'Thriller' ? 'selected' : ''}>Thriller</option>
                <option value="Animation" ${selectedGenre == 'Animation' ? 'selected' : ''}>Animation</option>
            </select>
        </form>
    </div>
    
    <div class="movie-grid">
        <c:choose>
            <c:when test="${empty movies}">
                <div class="no-movies">
                    <p>No movies available at the moment.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="movie" items="${movies}">
                    <div class="movie-card">
                        <div class="movie-poster">
                            <c:choose>
                                <c:when test="${not empty movie.imagePath}">
                                    <img src="${pageContext.request.contextPath}/${movie.imagePath}" alt="${movie.title}">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/images/no-poster.jpg" alt="No Poster Available">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="movie-info">
                            <h3>${movie.title}</h3>
                            <p>${movie.genre} | ${movie.duration} min</p>
                            <a href="${pageContext.request.contextPath}/movie/${movie.movieId}" class="btn btn-primary">View Details</a>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
