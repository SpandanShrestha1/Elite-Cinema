<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Home" />
    <jsp:param name="customJS" value="slider.js" />
</jsp:include>

<!-- Hero Section with Banner Slider -->
<c:choose>
    <c:when test="${not empty banners}">
        <div class="hero-section">
            <div class="hero-slider">
                <c:forEach var="banner" items="${banners}" varStatus="status">
                    <div class="slide ${status.index == 0 ? 'active' : ''}">
                        <img src="${pageContext.request.contextPath}/${banner.imagePath}" alt="${banner.title}">
                        <div class="slide-content">
                            <h2>${banner.title}</h2>
                            <p>${banner.description}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="slider-controls">
                <button class="prev-btn"><i class="fas fa-chevron-left"></i></button>
                <div class="slider-dots">
                    <c:forEach var="banner" items="${banners}" varStatus="status">
                        <span class="dot ${status.index == 0 ? 'active' : ''}" data-index="${status.index}"></span>
                    </c:forEach>
                </div>
                <button class="next-btn"><i class="fas fa-chevron-right"></i></button>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="hero-section simple-hero">
            <div class="slide-content">
                <h2>Welcome to Elite Cinema</h2>
                <p>Experience the best movies in town</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>

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
            <c:when test="${empty nowShowingMovies}">
                <div class="no-movies">
                    <p>No movies available at the moment.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach var="movie" items="${nowShowingMovies}">
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

    <!-- Upcoming Movies Section -->
    <c:if test="${not empty upcomingMovies}">
        <div class="section-title upcoming-section">
            <h2>Upcoming Movies</h2>
        </div>

        <div class="movie-grid upcoming-movies">
            <c:forEach var="movie" items="${upcomingMovies}">
                <div class="movie-card upcoming">
                    <div class="movie-poster">
                        <div class="release-date">
                            <fmt:formatDate value="${movie.releaseDate}" pattern="MMM dd, yyyy" />
                        </div>
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
                        <a href="${pageContext.request.contextPath}/movie/${movie.movieId}" class="btn btn-secondary">View Details</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<jsp:include page="common/footer.jsp" />
