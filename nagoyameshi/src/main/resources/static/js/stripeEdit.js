const stripe = Stripe('pk_test_51PZr0JLJ2LcWpnob4luZBrJD98cHEpLTAAelVVkNEnMIh6ccM0AWvTYjZYQdqaGB4NDX6K49ol4HynL66ijeO91p00UClyBd21');
const updateCardLinkButton = document.querySelector('#updateCardLink');

updateCardLinkButton.addEventListener('click', () => {
    fetch('/create-update-card-session', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ stripeCustomerId: stripeCustomerId })
    })
    .then(response => response.json())
    .then(data => {
        return stripe.redirectToCheckout({ sessionId: data.id });
    })
    .catch(error => {
        console.error('Error:', error);
    });
});