document.addEventListener('DOMContentLoaded', function() {
	var stripe = Stripe('pk_test_51P9GPqHhMxlEE4T1BFMmNNecyFFaMJ8h5Ra64MzntUvrzYvLdI0QD8ar1cz7V0Zer5Wr6w8gdD6FVtT7LtKHjsnw00ot3bYuAx');
	var elements = stripe.elements();
	// 各要素の作成
	var cardNumber = elements.create('cardNumber');
	var cardExpiry = elements.create('cardExpiry');
	var cardCvc = elements.create('cardCvc');

	// 各要素を指定されたdivにマウント
	cardNumber.mount('#card-number-element');
	cardExpiry.mount('#card-expiry-element');
	cardCvc.mount('#card-cvc-element');

	// エラーメッセージの表示
	var displayError = document.getElementById('card-errors');
	[cardNumber, cardExpiry, cardCvc].forEach(function(element) {
		element.on('change', function(event) {
			if (event.error) {
				displayError.textContent = event.error.message;
			} else {
				displayError.textContent = '';
			}
		});
	});

	// フォーム送信時の処理
	var form = document.getElementById('payment-form');
	form.addEventListener('submit', function(event) {
		event.preventDefault();

		stripe.createPaymentMethod({
			type: 'card',
			card: cardNumber
		}).then(function(result) {
			if (result.error) {
				displayError.textContent = result.error.message;
			} else {
				var csrfToken = document.getElementById('csrfToken').value;
				fetch('/stripeUpdate', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
						'X-CSRF-TOKEN': csrfToken
					},
					body: JSON.stringify({ paymentMethodId: result.paymentMethod.id })
				}).then(function(response) {
					return response.json();
				}).then(function(data) {
					console.log(data);
					if (data.success) {
						alert('カード情報が正常に更新されました！');
						window.location.href = '/user';
					} else {
						alert('エラー: ' + data.error);
					}
				}).catch(function(error) {
					console.error('Error:', error);
				});
			}
		});
	});
});