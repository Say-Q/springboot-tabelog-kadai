const stripe = Stripe('pk_test_51P9GPqHhMxlEE4T1BFMmNNecyFFaMJ8h5Ra64MzntUvrzYvLdI0QD8ar1cz7V0Zer5Wr6w8gdD6FVtT7LtKHjsnw00ot3bYuAx');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
	stripe.redirectToCheckout({
		sessionId: sessionId
	})
});
