document.addEventListener('DOMContentLoaded', async () => {
	const reviewButton = document.getElementById('googleReviewButton');
	const shopName = document.getElementById('shop-name').textContent.trim(); // HTMLからショップ名を取得
	const { PlacesService } = await google.maps.importLibrary("places");

	// ジオコーダーを作成
	const geocoder = new google.maps.Geocoder();
	// 住所を位置情報に変換
	geocoder.geocode({ address: shopName }, (results, status) => {

		if (status === 'OK') {

			reviewButton.addEventListener('click', () => {
			// Place APIを使用して追加情報を取得
			const request = {
				placeId: results[0].place_id,
				fields: ['name', 'place_id'],
			};

			// Google Places APIのリクエストを作成
			const service = new PlacesService(document.createElement('div'));
			service.getDetails(request, (place, status) => {

				if (status === google.maps.places.PlacesServiceStatus.OK) {

						// Googleマップの検索結果リンクを生成
						const googleMapsLink = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(place.name)}`;
						window.open(googleMapsLink, '_blank', 'noopener noreferrer');
				} else {
					console.error('Place ID could not be found for the following reason: ' + status);
				}
			});
					});
			
		} else {
			console.error('Geocode was not successful for the following reason: ' + status);
		}
	});
});