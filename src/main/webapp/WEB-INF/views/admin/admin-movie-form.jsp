<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${empty movie ? 'Add Movie' : 'Edit Movie'}" />
    <jsp:param name="isAdmin" value="true" />
</jsp:include>

<div class="admin-container">
    <jsp:include page="../common/admin-sidebar.jsp">
        <jsp:param name="active" value="movies" />
    </jsp:include>

    <div class="admin-content">
        <div class="admin-header">
            <h2>${empty movie ? 'Add New Movie' : 'Edit Movie'}</h2>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>

        <div class="admin-form">
            <form action="${pageContext.request.contextPath}/admin/movie/${empty movie ? 'add' : 'edit'}" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="${empty movie ? 'add' : 'edit'}">
                <c:if test="${not empty movie}">
                    <input type="hidden" name="movieId" value="${movie.movieId}">
                </c:if>

                <div class="form-row">
                    <div class="form-col">
                        <div class="form-group">
                            <label for="title">Movie Title</label>
                            <input type="text" id="title" name="title" value="${movie.title}" required>
                        </div>

                        <div class="form-group">
                            <label for="genre">Genre</label>
                            <select id="genre" name="genre" required>
                                <option value="">Select Genre</option>
                                <option value="Action" ${movie.genre == 'Action' ? 'selected' : ''}>Action</option>
                                <option value="Comedy" ${movie.genre == 'Comedy' ? 'selected' : ''}>Comedy</option>
                                <option value="Drama" ${movie.genre == 'Drama' ? 'selected' : ''}>Drama</option>
                                <option value="Horror" ${movie.genre == 'Horror' ? 'selected' : ''}>Horror</option>
                                <option value="Sci-Fi" ${movie.genre == 'Sci-Fi' ? 'selected' : ''}>Sci-Fi</option>
                                <option value="Romance" ${movie.genre == 'Romance' ? 'selected' : ''}>Romance</option>
                                <option value="Thriller" ${movie.genre == 'Thriller' ? 'selected' : ''}>Thriller</option>
                                <option value="Animation" ${movie.genre == 'Animation' ? 'selected' : ''}>Animation</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="duration">Duration (minutes)</label>
                            <input type="number" id="duration" name="duration" value="${movie.duration}" min="1" required>
                        </div>

                        <div class="form-group">
                            <label for="releaseDate">Release Date</label>
                            <input type="date" id="releaseDate" name="releaseDate"
                                   value="<fmt:formatDate value="${movie.releaseDate}" pattern="yyyy-MM-dd" />" required>
                        </div>

                        <div class="form-group">
                            <label for="status">Status</label>
                            <select id="status" name="status" required>
                                <option value="now_showing" ${empty movie.status || movie.status == 'now_showing' ? 'selected' : ''}>Now Showing</option>
                                <option value="upcoming" ${movie.status == 'upcoming' ? 'selected' : ''}>Upcoming</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-col">
                        <div class="form-group">
                            <label for="image">Movie Poster</label>
                            <div class="image-preview">
                                <c:if test="${not empty movie.imagePath}">
                                    <img src="${pageContext.request.contextPath}/${movie.imagePath}" alt="${movie.title}">
                                </c:if>
                            </div>
                            <input type="file" id="image" name="image" accept="image/*">
                            <c:if test="${not empty movie.imagePath}">
                                <p class="image-note">Leave empty to keep current image</p>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="5" required>${movie.description}</textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">${empty movie ? 'Add Movie' : 'Update Movie'}</button>
                    <a href="${pageContext.request.contextPath}/admin/movies" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
