// 地図を初期化して追加
let map;

async function initMap() {
    // 必要なライブラリを追加
    //@ts-ignore
    const { Map } = await google.maps.importLibrary("maps");
    const { AdvancedMarkerView } = await google.maps.importLibrary("marker");

    // ジオコーダーを作成
    const geocoder = new google.maps.Geocoder();

    // HTMLから住所を取得
    const address = document.getElementById('address').textContent.trim();

    // 住所を位置情報に変換
    geocoder.geocode({ address: address }, (results, status) => {
        if (status === 'OK') {
            const position = results[0].geometry.location;

            // 地図を作成
            map = new Map(document.getElementById("map"), {
                zoom: 15,
                center: position,
                mapId: "DEMO_MAP_ID",
            });

            // マーカーを追加
            const marker = new AdvancedMarkerView({
                map: map,
                position: position,
                title: address,
            });
        } else {
            console.error('Geocode was not successful for the following reason: ' + status);
        }
    });
}

// ページがロードされたら地図を初期化
window.onload = initMap;




//		fields: ["name", "formatted_address", "place_id", "geometry", "icon", "formatted_phone_number", "business_status",
//			"opening_hours", "PlacePhoto", "website", "price_level", "rating", "reviews", "user_ratings_total"],
