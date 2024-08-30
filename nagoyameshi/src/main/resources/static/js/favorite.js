const addFavoriteDiv = document.getElementById("add-favorite-div");
const deleteFavoriteDiv = document.getElementById("delete-favorite-div");
const addFavoriteForm = document.getElementById("add-favorite-form");
const deleteFavoriteForm = document.getElementById("delete-favorite-form");

toggleFavoriteButtons(hasFavorites); // 初期状態を設定

addFavoriteForm ? handleFavoriteSubmit(addFavoriteForm, true) : null;
deleteFavoriteForm ? handleFavoriteSubmit(deleteFavoriteForm, false) : null;

//フォーム送信処理
function handleFavoriteSubmit(form, sholdFavorite) {
	form.addEventListener("submit", function(event) {
		event.preventDefault(); //デフォルトの操作をキャンセル
		const formData = new FormData(form);
		fetch(form.action, {
			method: 'POST',
			body: formData
		}).then(response => {
			if (response.ok) {
				toggleFavoriteButtons(sholdFavorite);
			} else {
				return response.text().then(text => { throw new Error(text); });
			}
		}).catch(error => console.error('エラー:', error.message));
	});
}

// ボタン表示を切り替える関数
function toggleFavoriteButtons(hasFavorites) {
	addFavoriteDiv.style.display = hasFavorites ? 'none' : 'block';
	deleteFavoriteDiv.style.display = hasFavorites ? 'block' : 'none';
}