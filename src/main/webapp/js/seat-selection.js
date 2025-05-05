// JavaScript for seat selection functionality

document.addEventListener('DOMContentLoaded', function() {
    const seats = document.querySelectorAll('.seat.available');
    const selectedSeatsElement = document.getElementById('selectedSeats');
    const totalPriceElement = document.getElementById('totalPrice');
    const seatSelectionForm = document.getElementById('seatSelectionForm');
    const continueButton = document.getElementById('continueButton');
    
    let selectedSeats = [];
    let pricePerSeat = 0;
    
    // Get price per seat from data attribute
    if (totalPriceElement) {
        pricePerSeat = parseFloat(totalPriceElement.dataset.price || 0);
    }
    
    // Add click event to seats
    seats.forEach(function(seat) {
        seat.addEventListener('click', function() {
            const seatNumber = this.dataset.seat;
            
            if (this.classList.contains('selected')) {
                // Deselect seat
                this.classList.remove('selected');
                selectedSeats = selectedSeats.filter(s => s !== seatNumber);
            } else {
                // Select seat
                this.classList.add('selected');
                selectedSeats.push(seatNumber);
            }
            
            // Update selected seats display
            if (selectedSeatsElement) {
                selectedSeatsElement.textContent = selectedSeats.join(', ');
            }
            
            // Update total price
            if (totalPriceElement) {
                const totalPrice = selectedSeats.length * pricePerSeat;
                totalPriceElement.textContent = totalPrice.toFixed(2);
            }
            
            // Enable/disable continue button
            if (continueButton) {
                continueButton.disabled = selectedSeats.length === 0;
            }
        });
    });
    
    // Handle form submission
    if (seatSelectionForm) {
        seatSelectionForm.addEventListener('submit', function(event) {
            if (selectedSeats.length === 0) {
                event.preventDefault();
                alert('Please select at least one seat.');
            } else {
                // Add selected seats as hidden inputs
                selectedSeats.forEach(function(seat) {
                    const input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = 'seats';
                    input.value = seat;
                    seatSelectionForm.appendChild(input);
                });
            }
        });
    }
});
