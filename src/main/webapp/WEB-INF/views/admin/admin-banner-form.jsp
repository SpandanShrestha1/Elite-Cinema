<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${empty banner ? 'Add Banner' : 'Edit Banner'}" />
    <jsp:param name="isAdmin" value="true" />
</jsp:include>

<div class="admin-container">
    <jsp:include page="../common/admin-sidebar.jsp">
        <jsp:param name="active" value="banners" />
    </jsp:include>

    <div class="admin-content">
        <div class="admin-header">
            <h2>${empty banner ? 'Add New Banner' : 'Edit Banner'}</h2>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                ${error}
            </div>
        </c:if>

        <div class="admin-form">
            <form action="${pageContext.request.contextPath}/admin/banner/${empty banner ? 'add' : 'edit'}" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
                <input type="hidden" name="action" value="${empty banner ? 'add' : 'edit'}">
                <c:if test="${not empty banner}">
                    <input type="hidden" name="bannerId" value="${banner.bannerId}">
                </c:if>

                <div class="form-group">
                    <label for="title">Title</label>
                    <input type="text" id="title" name="title" value="${banner.title}" required>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="3">${banner.description}</textarea>
                </div>

                <div class="form-group">
                    <label for="image">Banner Image</label>
                    <div class="image-preview">
                        <c:if test="${not empty banner.imagePath}">
                            <img src="${pageContext.request.contextPath}/${banner.imagePath}" alt="${banner.title}">
                        </c:if>
                    </div>
                    <input type="file" id="image" name="image" accept="image/jpeg,image/png,image/gif" ${empty banner ? 'required' : ''}>
                    <p class="file-help">Accepted formats: JPG, PNG, GIF. Max size: 5MB.</p>
                    <c:if test="${not empty banner.imagePath}">
                        <p class="image-note">Leave empty to keep current image</p>
                    </c:if>
                </div>

                <div class="form-group">
                    <label class="checkbox-label">
                        <input type="checkbox" id="active" name="active" ${empty banner || banner.active ? 'checked' : ''}>
                        Active
                    </label>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">${empty banner ? 'Add Banner' : 'Update Banner'}</button>
                    <a href="${pageContext.request.contextPath}/admin/banners" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp" />
