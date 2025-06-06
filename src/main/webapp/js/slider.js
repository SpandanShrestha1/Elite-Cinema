// JavaScript for Hero Banner Slider

document.addEventListener('DOMContentLoaded', function() {
    console.log('Slider script loaded');

    // Get slider elements
    const slides = document.querySelectorAll('.slide');
    const dots = document.querySelectorAll('.dot');
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');

    console.log('Found ' + slides.length + ' slides');
    console.log('Found ' + dots.length + ' dots');

    // If no slides, don't initialize the slider
    if (slides.length === 0) {
        console.log('No slides found, skipping slider initialization');
        return;
    }

    // Initialize variables
    let currentSlide = 0;
    let slideInterval;
    const slideDelay = 5000; // 5 seconds

    // Function to show a specific slide
    function showSlide(index) {
        // Remove active class from all slides and dots
        slides.forEach(slide => slide.classList.remove('active'));
        dots.forEach(dot => dot.classList.remove('active'));

        // Add active class to current slide and dot
        slides[index].classList.add('active');
        dots[index].classList.add('active');

        // Update current slide index
        currentSlide = index;
    }

    // Function to show next slide
    function nextSlide() {
        let nextIndex = currentSlide + 1;
        if (nextIndex >= slides.length) {
            nextIndex = 0;
        }
        showSlide(nextIndex);
    }

    // Function to show previous slide
    function prevSlide() {
        let prevIndex = currentSlide - 1;
        if (prevIndex < 0) {
            prevIndex = slides.length - 1;
        }
        showSlide(prevIndex);
    }

    // Start automatic slideshow
    function startSlideshow() {
        slideInterval = setInterval(nextSlide, slideDelay);
    }

    // Stop automatic slideshow
    function stopSlideshow() {
        clearInterval(slideInterval);
    }

    // Add event listeners to dots
    if (dots.length > 0) {
        console.log('Adding event listeners to dots');
        dots.forEach((dot, index) => {
            dot.addEventListener('click', () => {
                console.log('Dot clicked: ' + index);
                showSlide(index);
                stopSlideshow();
                startSlideshow();
            });
        });
    }

    // Add event listeners to prev/next buttons
    if (prevBtn) {
        console.log('Adding event listener to prev button');
        prevBtn.addEventListener('click', () => {
            console.log('Prev button clicked');
            prevSlide();
            stopSlideshow();
            startSlideshow();
        });
    }

    if (nextBtn) {
        console.log('Adding event listener to next button');
        nextBtn.addEventListener('click', () => {
            console.log('Next button clicked');
            nextSlide();
            stopSlideshow();
            startSlideshow();
        });
    }

    // Pause slideshow on hover
    const heroSection = document.querySelector('.hero-section');
    if (heroSection) {
        heroSection.addEventListener('mouseenter', stopSlideshow);
        heroSection.addEventListener('mouseleave', startSlideshow);
    }

    // Start slideshow
    console.log('Starting slideshow');
    startSlideshow();
    console.log('Slideshow started');
});
