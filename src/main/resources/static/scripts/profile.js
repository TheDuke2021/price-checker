"use strict"

const result = $(".result")[0];

//Change avatar logic
$(".account-card__settings__change-avatar").click(function() {
    $("#change-avatar-form input")[0].click();
    $("#change-avatar-form input").change(function() {
        $("#change-avatar-form")[0].submit();
    });
});

//Change password logic
$(".account-card__settings__change-password").click(function() {
    this.style.display = "none";
    $("#change-password-form")[0].hidden = false;
});

$("#change-password-form").blur(function() {
    this.hidden = true;
    $(".account-card__settings__change-password")[0].style.display = "block";
});

$(".account-card__settings-change-password__cancel").click(function() {
    $("#change-password-form")[0].hidden = true;
    $(".account-card__settings__change-password")[0].style.display = "block";
});

$(".account-card__settings-change-password__confirm").click(function() {
    const password = $("#password").val().trim();
    const confirmPassword = $("#confirmPassword").val().trim();
    if(password == "" || confirmPassword == "") {
        alert("Заполните все поля");
            return;
    }
    if(password != confirmPassword) {
        alert("Пароли не совпадают");
        return;
    }

    $("#change-password-form")[0].submit();
});

function loadFavoriteItems() {

    for(let i = 0; i < favoriteItems.length; i++) {
        const photoURL = favoriteItems[i].item.photoURL;
        const name = favoriteItems[i].item.name;
         let price = favoriteItems[i].item.price;
            if(price == APPLICATION.null_price)
                price = "Нет цены";
            else
                price = price.toLocaleString('en-US').replaceAll(",", " ") + "р";
        const URL = favoriteItems[i].item.url;
        const shop = favoriteItems[i].item.shop;

        const resultItem = createResultItem(URL, photoURL, name, price, shop);
//        const resultItemButton = resultItem.getElementsByClassName("item-favorite")[0];
//        resultItemButton.getElementsByTagName("i")[0].classList.remove("bi-star");
//        resultItemButton.getElementsByTagName("i")[0].classList.add("bi-star-fill");

        result.appendChild(resultItem);
    }
}

window.onload = loadFavoriteItems;