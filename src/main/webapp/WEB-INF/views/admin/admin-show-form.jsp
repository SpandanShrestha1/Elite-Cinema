<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${empty show ? 'Add Show' : 'Edit Show'}" />
    <jsp:param name="isAdmin" value="true" />
</jsp:include>

<div class="admin-container">
    <jsp:include page="../common/admin-sidebar.jsp">
        <jsp:param name="active" value="shows" />
    </jsp:include>
    
    <div class="admin-content">
        <div class="admin-header">
            <h2>${empty show ? 'Add New Show' : 'Edit Show'}</h2>
        </div>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>
        
        <div class="admin-form">
            <form action="${pageContext.request.contextPath}/admin/show/${empty show ? 'add' : 'edit'}" method="post">
                <input type="hidden" name="action" value="${empty show ? 'add' : 'edit'}">
                <c:if test="${not empty show}">
                    <input type="hidden" name="showId" value="${show.showId}">
                </c:if>
                
                <div class="form-group">
                    <label for="movieId">Movie</label>
                    <select id="movieId" name="movieId" required>
                        <option value="">Select Movie</option>
                        <c:forEach var="movie" items="${movies}">
                            <option value="${movie.movieId}" ${show.movieId == movie.movieId ? 'selected' : ''}>
                                ${movie.title}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-row">
                    <div class="form-col">
                        <div class="form-group">
                            <label for="date">Date</label>
                            <input type="date" id="date" name="date" 
                                   value="<fmt:formatDate value="${show.date}" pattern="yyyy-MM-dd" />" required>
                        </div>
                    </div>
                    <div class="form-col">
                        <div class="form-group">
                            <label for="time">Time</label>
                            <input type="time" id="time" name="time" 
                                   value="<fmt:formatDate value="${show.time}" pattern="HH:mm" />" required>
                        </div>
                    </div>
                </div>
                
                <div class="form-row">
                    <div class="form-col">
                        <div class="form-group">
                            <label for="totalSeats">Total Seats</label>
                            <input type="number" id="totalSeats" name="totalSeats" 
                                   value="${empty show ? '100' : show.totalSeats}" min="1" required>
                        </div>
                    </div>
                    <div class="form-col">
                        <c:if test="${not empty show}">
                            <div class="form-group">
                                <label for="availableSeats">Available Seats</label>
                                <input type="number" id="availableSeats" name="availableSeats" 
                                       value="${show.availableSeats}" min="0" max="${show.totalSeats}" required>
                            </div>
                        </c:if>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="price">Ticket Price ($)</label>
                    <input type="number" id="price" name="price" 
                           value="${empty show ? '10.00' : show.price}" min="0.01" step="0.01" required>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">${empty show ? 'Add Show' : 'Update Show'}</button>
                    <a href="${pageContext.request.contextPath}/admin/shows" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
