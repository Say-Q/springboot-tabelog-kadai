document.addEventListener("DOMContentLoaded", function() {
	document.getElementById("cancel-subscription").onclick = function(event) {
		event.preventDefault();  // リンクのデフォルト動作を停止
		// Bootstrapのモーダルを表示
		$("#cancelModal").modal('show');
	};

	document.getElementById("confirm-cancel").onclick = function() {
		// CSRFトークンを取得
		const csrfTokenMetaTag = document.querySelector('meta[name="csrf-token"]');
		if (!csrfTokenMetaTag) {
			alert('CSRFトークンが見つかりません.');
			return;
		}
		const csrfToken = csrfTokenMetaTag.getAttribute('content');

		// 解約リクエストを送信
		fetch('/cancel-subscription', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'X-CSRF-TOKEN': csrfToken
			},
			body: JSON.stringify({})
		})
			.then(response => response.json())
			.then(data => {
				// モーダルを閉じる
				$("#cancelModal").modal('hide');
				if (data.success) {
					// ログアウトリクエストを送信
					fetch('/logout', {
						method: 'POST',
						headers: {
							'Content-Type': 'application/json',
							'X-CSRF-TOKEN': csrfToken
						}
					})
					window.location.href = '/login';
					alert('有料会員の解約が完了しました。ログイン画面へ遷移します。');

				} else {
					alert('解約に失敗しました。再度お試しください。' + (data.error ? " エラー: " + data.error : ""));
				}
			})
			.catch(error => {
				alert('解約に失敗しました。再度お試しください。');
				console.error('Error:', error);
			});
	};
});
