<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    </main>
    <footer>
        <div class="footer-content">
            <div class="footer-section about">
                <h2>About Us</h2>
                <p>Elite Cinema is a premier movie theater chain providing the best movie experience with state-of-the-art technology and comfortable seating.</p>
            </div>
            <div class="footer-section links">
                <h2>Quick Links</h2>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
                    <li><a href="${pageContext.request.contextPath}/gallery">Gallery</a></li>
                    <li><a href="${pageContext.request.contextPath}/ticket-rate">Ticket Rate</a></li>
                </ul>
            </div>
            <div class="footer-section contact">
                <h2>Contact Us</h2>
                <p><i class="fa fa-map-marker"></i> 123 Movie Street, Kathmandu, Nepal</p>
                <p><i class="fa fa-phone"></i> +977 1234567890</p>
                <p><i class="fa fa-envelope"></i> info@elitecinema.com</p>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2023 Elite Cinema. All Rights Reserved.</p>
        </div>
    </footer>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <c:if test="${param.customJS != null}">
        <script src="${pageContext.request.contextPath}/js/${param.customJS}"></script>
    </c:if>
</body>
</html>
