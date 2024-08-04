// 地図を初期化して追加
let map;

async function initMap() {
	// 必要なライブラリを追加
	//@ts-ignore
	const { Map } = await google.maps.importLibrary("maps");
	const { AdvancedMarkerElement } = await google.maps.importLibrary("marker");
	const { PlacesService } = await google.maps.importLibrary("places");

	// ジオコーダーを作成
	const geocoder = new google.maps.Geocoder();

	// HTMLからショップ名を取得
	const shopName = document.getElementById('shop-name').textContent.trim();

	// 住所を位置情報に変換
	geocoder.geocode({ address: shopName }, (results, status) => {
		if (status === 'OK') {
			const position = results[0].geometry.location;

			// 地図を作成
			map = new Map(document.getElementById("map"), {
				zoom: 18,
				center: position,
				mapId: 'DEMO_MAP_ID',
				fullscreenControl: false,
			});

			// マーカーを追加
			const marker = new google.maps.marker.AdvancedMarkerElement({
				map: map,
				position: position,
				title: shopName,
			});

			// 情報ウィンドウを追加
			let infowindow = new google.maps.InfoWindow();

			// マーカーをクリックしたときに情報ウィンドウを表示
			marker.addListener("click", () => {
				// Place APIを使用して追加情報を取得
				const request = {
					placeId: results[0].place_id,
					fields: ['name', 'formatted_address', 'opening_hours', 'rating', 'user_ratings_total'],
				};

				const service = new PlacesService(map);
				service.getDetails(request, (place, status) => {

					if (status === google.maps.places.PlacesServiceStatus.OK) {
						// Googleマップの検索結果リンクを生成
						const googleMapsLink = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(place.name)}`;

						// 取得した情報を情報ウィンドウに表示
						const content = `
							<div><strong>${place.name}</strong><span style="color:gold;"> ★</span><span style="font-weight:bold;">${place.rating || '評価なし'}(${place.user_ratings_total || '0'}件)</span></div>
							<div><住所><a href="${googleMapsLink}" target="_blank"> Googleマップで開く</a>
							<br>${place.formatted_address}</div>
							<div><営業時間><br> ${place.opening_hours ? place.opening_hours.weekday_text.join('<br>') : '情報なし'}</div>
						`;
						infowindow.setContent(content);
						infowindow.open(map, marker);
					} else {
						console.error('Place details request failed due to ' + status);
					}
				});
			});

		} else {
			console.error('Geocode was not successful for the following reason: ' + status);
		}
	});
}

// ページがロードされたら地図を初期化
window.onload = initMap;