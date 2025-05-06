// JavaScript for seat selection functionality

document.addEventListener('DOMContentLoaded', function() {
    console.log('Seat selection script loaded');

    // Get DOM elements
    const seats = document.querySelectorAll('.seat.available');
    const selectedSeatsElement = document.getElementById('selectedSeats');
    const totalPriceElement = document.getElementById('totalPrice');
    const seatSelectionForm = document.getElementById('seatSelectionForm');
    const continueButton = document.getElementById('continueButton');

    // Initialize variables
    let selectedSeats = [];
    let pricePerSeat = 0;

    console.log('Found ' + seats.length + ' available seats');
    console.log('Selected seats element:', selectedSeatsElement);
    console.log('Total price element:', totalPriceElement);
    console.log('Form element:', seatSelectionForm);
    console.log('Continue button:', continueButton);

    // Get price per seat from data attribute
    if (totalPriceElement) {
        pricePerSeat = parseFloat(totalPriceElement.dataset.price || 0);
        console.log('Price per seat:', pricePerSeat);
    }

    // Add click event to seats
    seats.forEach(function(seat) {
        seat.addEventListener('click', function() {
            const seatNumber = this.dataset.seat;
            console.log('Seat clicked:', seatNumber);

            if (this.classList.contains('selected')) {
                // Deselect seat
                console.log('Deselecting seat:', seatNumber);
                this.classList.remove('selected');
                selectedSeats = selectedSeats.filter(s => s !== seatNumber);
            } else {
                // Select seat
                console.log('Selecting seat:', seatNumber);
                this.classList.add('selected');
                selectedSeats.push(seatNumber);
            }

            console.log('Selected seats:', selectedSeats);

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
            console.log('Form submitted');

            if (selectedSeats.length === 0) {
                console.log('No seats selected, preventing form submission');
                event.preventDefault();
                alert('Please select at least one seat.');
            } else {
                console.log('Adding selected seats to form:', selectedSeats);

                // Clear any existing hidden inputs
                const existingInputs = seatSelectionForm.querySelectorAll('input[name="seats"]');
                existingInputs.forEach(input => input.remove());

                // Add selected seats as hidden inputs
                selectedSeats.forEach(function(seat) {
                    const input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = 'seats';
                    input.value = seat;
                    seatSelectionForm.appendChild(input);
                    console.log('Added hidden input for seat:', seat);
                });

                console.log('Form submission proceeding with seats:', selectedSeats);
            }
        });
    }

    // Add debug button to check if JavaScript is working
    const debugSection = document.createElement('div');
    debugSection.style.marginTop = '20px';
    debugSection.style.padding = '10px';
    debugSection.style.border = '1px solid #ddd';
    debugSection.style.borderRadius = '5px';
    debugSection.style.backgroundColor = '#f9f9f9';

    const debugTitle = document.createElement('h4');
    debugTitle.textContent = 'Debug Info';
    debugSection.appendChild(debugTitle);

    const debugInfo = document.createElement('p');
    debugInfo.textContent = 'JavaScript is working properly. Click on seats to select them.';
    debugSection.appendChild(debugInfo);

    const debugButton = document.createElement('button');
    debugButton.textContent = 'Test Selection';
    debugButton.className = 'btn btn-secondary';
    debugButton.style.marginTop = '10px';
    debugButton.addEventListener('click', function(e) {
        e.preventDefault();
        alert('Currently selected seats: ' + (selectedSeats.length > 0 ? selectedSeats.join(', ') : 'None'));
    });
    debugSection.appendChild(debugButton);

    // Add debug section to the page
    document.querySelector('.seat-selection').appendChild(debugSection);
});
