<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Admin - Movies" />
    <jsp:param name="isAdmin" value="true" />
</jsp:include>

<div class="admin-container">
    <jsp:include page="../common/admin-sidebar.jsp">
        <jsp:param name="active" value="movies" />
    </jsp:include>

    <div class="admin-content">
        <div class="admin-header">
            <h2>Movie Management</h2>
            <a href="${pageContext.request.contextPath}/admin/movie/add" class="btn btn-primary">Add New Movie</a>
        </div>

        <div class="admin-table">
            <c:choose>
                <c:when test="${empty movies}">
                    <p>No movies available.</p>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Poster</th>
                                <th>Title</th>
                                <th>Genre</th>
                                <th>Duration</th>
                                <th>Release Date</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="movie" items="${movies}">
                                <tr>
                                    <td>${movie.movieId}</td>
                                    <td class="movie-thumbnail">
                                        <c:choose>
                                            <c:when test="${not empty movie.imagePath}">
                                                <img src="${pageContext.request.contextPath}/${movie.imagePath}" alt="${movie.title}">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${pageContext.request.contextPath}/images/no-poster.jpg" alt="No Poster Available">
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${movie.title}</td>
                                    <td>${movie.genre}</td>
                                    <td>${movie.duration} min</td>
                                    <td><fmt:formatDate value="${movie.releaseDate}" pattern="MMM dd, yyyy" /></td>
                                    <td>
                                        <span class="status ${movie.status == 'upcoming' ? 'upcoming' : 'now-showing'}">
                                            ${movie.status == 'upcoming' ? 'Upcoming' : 'Now Showing'}
                                        </span>
                                    </td>
                                    <td class="actions">
                                        <a href="${pageContext.request.contextPath}/movie/${movie.movieId}" class="btn btn-secondary">View</a>
                                        <a href="${pageContext.request.contextPath}/admin/movie/edit?id=${movie.movieId}" class="btn btn-primary">Edit</a>
                                        <a href="${pageContext.request.contextPath}/admin/movie/delete?id=${movie.movieId}"
                                           class="btn btn-danger"
                                           onclick="return confirm('Are you sure you want to delete this movie? This action cannot be undone.')">
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
