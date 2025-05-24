const stripe = Stripe('pk_test_51RDu3mBUES6M2scHCPkoIpggmDCv2ScRA1aIUYQYea4oKYuTZzhcMQosbZ4dWmQ1a0IbjCJpAGNs3iy10KxUAbdP00Uqrs5eaV');
const paymentButton = document.querySelector('#paymentButton');
 
 paymentButton.addEventListener('click', () => {
   stripe.redirectToCheckout({
     sessionId: sessionId
   })
 });;