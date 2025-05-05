<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Ticket Rates" />
</jsp:include>

<div class="container">
    <div class="ticket-rates">
        <div class="section-title">
            <h2>Ticket Rates</h2>
        </div>
        
        <table class="rate-table">
            <thead>
                <tr>
                    <th>Day</th>
                    <th>Regular Seat</th>
                    <th>Premium Seat</th>
                    <th>VIP Seat</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Monday - Thursday</td>
                    <td>$8.00</td>
                    <td>$10.00</td>
                    <td>$15.00</td>
                </tr>
                <tr>
                    <td>Friday</td>
                    <td>$10.00</td>
                    <td>$12.00</td>
                    <td>$18.00</td>
                </tr>
                <tr>
                    <td>Saturday - Sunday</td>
                    <td>$12.00</td>
                    <td>$15.00</td>
                    <td>$20.00</td>
                </tr>
                <tr>
                    <td>Public Holidays</td>
                    <td>$12.00</td>
                    <td>$15.00</td>
                    <td>$20.00</td>
                </tr>
            </tbody>
        </table>
        
        <div class="rate-notes">
            <h3>Notes:</h3>
            <ul>
                <li>Children under 3 years old are free of charge.</li>
                <li>Senior citizens (65+ years) get a 20% discount on all ticket types.</li>
                <li>Students with valid ID get a 10% discount on all ticket types.</li>
                <li>Special discounts available for group bookings (10+ tickets).</li>
                <li>3D movies have an additional charge of $3.00 per ticket.</li>
                <li>IMAX screenings have an additional charge of $5.00 per ticket.</li>
                <li>Prices are subject to change without prior notice.</li>
            </ul>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />
