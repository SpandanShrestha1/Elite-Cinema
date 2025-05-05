<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Contact Us" />
</jsp:include>

<div class="container">
    <div class="section-title">
        <h2>Contact Us</h2>
    </div>
    
    <div class="contact-container">
        <div class="contact-info">
            <h3>Get in Touch</h3>
            <p><i class="fa fa-map-marker"></i> 123 Movie Street, Kathmandu, Nepal</p>
            <p><i class="fa fa-phone"></i> +977 1234567890</p>
            <p><i class="fa fa-envelope"></i> info@elitecinema.com</p>
            
            <div class="map">
                <!-- Google Maps Embed would go here in a real application -->
                <div class="map-placeholder">
                    <p>Map Placeholder</p>
                </div>
            </div>
        </div>
        
        <div class="contact-form">
            <h3>Send us a Message</h3>
            
            <c:if test="${not empty param.success}">
                <div class="alert alert-success">
                    ${param.success}
                </div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/contact" method="post">
                <div class="form-group">
                    <label for="name">Your Name</label>
                    <input type="text" id="name" name="name" required>
                </div>
                
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" required>
                </div>
                
                <div class="form-group">
                    <label for="subject">Subject</label>
                    <input type="text" id="subject" name="subject" required>
                </div>
                
                <div class="form-group">
                    <label for="message">Message</label>
                    <textarea id="message" name="message" rows="5" required></textarea>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Send Message</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
