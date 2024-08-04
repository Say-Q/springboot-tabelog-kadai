function initialize() {
	document.addEventListener("DOMContentLoaded", function() {
		const apiKey = document.getElementById('shop-rating').getAttribute('data-api-key');
		const shopName = document.getElementById("shop-name").textContent;  // 店舗名を取得

		// ジオコーダーを作成
		const geocoder = new google.maps.Geocoder();
		// 住所を位置情報に変換
		geocoder.geocode({ address: shopName }, (results, status) => {

			if (status === 'OK') {

				// Place APIを使用して追加情報を取得
				const request = {
					placeId: results[0].place_id,
					fields: ['rating', 'user_ratings_total'],
				};

				// Google Places APIのリクエストを作成
				const service = new google.maps.places.PlacesService(document.createElement('div'));
				service.getDetails(request, (place, status) => {

					if (status === google.maps.places.PlacesServiceStatus.OK) {

						const rating = place.rating || "N/A";
						const userRatingsTotal = place.user_ratings_total || "N/A";

						document.getElementById("shop-rating").textContent = `評価: ${rating} (${userRatingsTotal}件の評価)`;
					} else {
						console.error('Place ID could not be found for the following reason: ' + status);
					}
				});

			} else {
				console.error('Geocode was not successful for the following reason: ' + status);
			}
		});
	});
}