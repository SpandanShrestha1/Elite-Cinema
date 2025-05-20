<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="Payment" />
</jsp:include>

<div class="container">
    <div class="form-container">
        <div class="form-title">
            <h2>Payment</h2>
        </div>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">
                ${param.error}
            </div>
        </c:if>

        <div class="payment-summary">
            <h3>Booking Summary</h3>
            <table>
                <tr>
                    <th>Movie</th>
                    <td>${show.movie.title}</td>
                </tr>
                <tr>
                    <th>Date</th>
                    <td><fmt:formatDate value="${show.date}" pattern="EEE, MMM dd, yyyy" /></td>
                </tr>
                <tr>
                    <th>Time</th>
                    <td><fmt:formatDate value="${show.time}" pattern="hh:mm a" /></td>
                </tr>
                <tr>
                    <th>Seats</th>
                    <td>
                        <c:forEach var="seat" items="${selectedSeats}" varStatus="status">
                            ${seat}<c:if test="${!status.last}">, </c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <th>Number of Seats</th>
                    <td>${selectedSeats.length}</td>
                </tr>
                <tr>
                    <th>Price per Seat</th>
                    <td>Rs. <fmt:formatNumber value="${show.price}" pattern="#,##0.00" /></td>
                </tr>
                <tr>
                    <th>Total Amount</th>
                    <td>Rs. <fmt:formatNumber value="${totalAmount}" pattern="#,##0.00" /></td>
                </tr>
            </table>
        </div>

        <div class="payment-methods">
            <h3>Select Payment Method</h3>
            <div class="payment-method-list">
                <div class="payment-method-item" data-method="credit-card">
                    <i class="fa fa-credit-card"></i> Credit Card
                </div>
                <div class="payment-method-item" data-method="paypal">
                    <i class="fa fa-paypal"></i> PayPal
                </div>
                <div class="payment-method-item" data-method="bank-transfer">
                    <i class="fa fa-bank"></i> Bank Transfer
                </div>
            </div>
        </div>

        <form id="paymentForm" action="${pageContext.request.contextPath}/booking/confirm" method="post">
            <input type="hidden" name="action" value="processPayment">

            <div class="payment-details">
                <!-- Credit Card Form (shown by default) -->
                <div class="payment-form credit-card-form">
                    <div class="form-group">
                        <label for="cardNumber">Card Number</label>
                        <input type="text" id="cardNumber" name="cardNumber" placeholder="1234 5678 9012 3456">
                    </div>
                    <div class="form-row">
                        <div class="form-col">
                            <label for="expiryDate">Expiry Date</label>
                            <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY">
                        </div>
                        <div class="form-col">
                            <label for="cvv">CVV</label>
                            <input type="text" id="cvv" name="cvv" placeholder="123">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="cardName">Name on Card</label>
                        <input type="text" id="cardName" name="cardName">
                    </div>
                </div>

                <!-- PayPal Form (hidden by default) -->
                <div class="payment-form paypal-form" style="display: none;">
                    <p>You will be redirected to PayPal to complete your payment.</p>
                </div>

                <!-- Bank Transfer Form (hidden by default) -->
                <div class="payment-form bank-transfer-form" style="display: none;">
                    <p>Please transfer the amount to the following bank account:</p>
                    <p>Bank: Elite Bank</p>
                    <p>Account Number: 1234567890</p>
                    <p>IFSC Code: ELITE0001234</p>
                </div>
            </div>

            <div class="form-actions">
                <button type="button" id="payNowBtn" class="btn btn-primary">Pay Now</button>
            </div>
        </form>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const paymentMethods = document.querySelectorAll('.payment-method-item');
        const paymentForms = document.querySelectorAll('.payment-form');

        paymentMethods.forEach(function(method) {
            method.addEventListener('click', function() {
                // Remove selected class from all methods
                paymentMethods.forEach(m => m.classList.remove('selected'));

                // Add selected class to clicked method
                this.classList.add('selected');

                // Hide all payment forms
                paymentForms.forEach(form => form.style.display = 'none');

                // Show selected payment form
                const methodName = this.dataset.method;
                const selectedForm = document.querySelector(`.${methodName}-form`);
                if (selectedForm) {
                    selectedForm.style.display = 'block';
                }
            });
        });

        // Select credit card by default
        paymentMethods[0].click();

        // Handle Pay Now button click
        const payNowBtn = document.getElementById('payNowBtn');
        const paymentForm = document.getElementById('paymentForm');

        if (payNowBtn && paymentForm) {
            payNowBtn.addEventListener('click', function() {
                // Show confirmation dialog
                Swal.fire({
                    title: 'Confirm Booking',
                    text: 'Are you sure you want to proceed with this booking?',
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#e50914',
                    cancelButtonColor: '#333',
                    confirmButtonText: 'Yes, book now!',
                    cancelButtonText: 'Cancel'
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Show processing message
                        Swal.fire({
                            title: 'Processing Payment',
                            text: 'Please wait while we process your payment...',
                            icon: 'info',
                            allowOutsideClick: false,
                            showConfirmButton: false,
                            willOpen: () => {
                                Swal.showLoading();
                            }
                        });

                        // Submit the form after a short delay to show the loading animation
                        setTimeout(() => {
                            paymentForm.submit();
                        }, 1500);
                    }
                });
            });
        }
    });
</script>

<jsp:include page="common/footer.jsp" />
