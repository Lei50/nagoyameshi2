document.querySelectorAll('.delete-category-form').forEach(form => {
    form.addEventListener('submit', function (event) {
        event.preventDefault(); // デフォルトのフォーム送信を防ぐ
        const actionUrl = this.action;

        fetch(actionUrl, {
            method: 'POST'
        })
        .then(response => {
            return response.text().then(text => {
                if (response.ok) {
                    alert(text); // サーバーの成功メッセージを表示
                    location.reload(); // ページをリロード
                } else {
                    alert(text); // サーバーのエラーメッセージを表示
                }
            });
        })
        .catch(error => {
            alert("エラーが発生しました: " + error.message);
        });
    });
});