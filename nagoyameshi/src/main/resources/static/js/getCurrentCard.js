document.addEventListener('DOMContentLoaded', function() {
	fetch('/getCurrentCard')
		.then(response => response.json())
		.then(data => {
			if (data.cardLast4) {
				document.getElementById('card-number-display').textContent = `**** **** **** ${data.cardLast4}`;
				document.getElementById('card-expiry-display').textContent = `${data.expMonth}/${data.expYear}`;
			} else {
				console.error('No card data found or error occurred:', data.error);
			}
		})
		.catch(error => console.error('Error fetching card data:', error));
});
