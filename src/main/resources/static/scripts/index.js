"use strict"


const result = $("#result")[0];



function createResultItem(URL, photoURL, name, price, shop) {
    const item = document.createElement("div");
    item.classList.add("result-item");

    const itemPhoto = document.createElement("div");
    itemPhoto.classList.add("item-photo");
    itemPhoto.style.backgroundImage = 'url("' + photoURL + '")';
    item.appendChild(itemPhoto);

    const itemInfo = document.createElement("div");
    itemInfo.classList.add("result-item__info");
    const itemInfoA = document.createElement("a");
    itemInfoA.classList.add("item-name");
    itemInfoA.setAttribute("href", URL);
    itemInfoA.textContent = name;
    itemInfo.appendChild(itemInfoA);

    const itemInfoBottom = document.createElement("div");
    itemInfoBottom.classList.add("result-item__info__bottom");
    const itemInfoBottomButton = document.createElement("button");
    itemInfoBottomButton.classList.add("item-favorite");
    itemInfoBottomButton.innerHTML = '<i class="bi bi-star"></i>';
    itemInfoBottom.appendChild(itemInfoBottomButton);

    const itemInfoBottomImg = new Image();
    let logoSrc = "";
    switch(shop){
        case "DNS":
            logoSrc += APPLICATION.logos.dns;
            break;
        case "REGARD":
            logoSrc += APPLICATION.logos.regard;
            break;
        case "CITILINK":
            logoSrc += APPLICATION.logos.citilink;
            break;
    }
    itemInfoBottomImg.src = logoSrc;
    itemInfoBottomImg.classList.add("item-logo");
    itemInfoBottom.appendChild(itemInfoBottomImg);

    const itemInfoBottomH3 = document.createElement("h3");
    itemInfoBottomH3.classList.add("item-price");
    itemInfoBottomH3.textContent = price;
    itemInfoBottom.appendChild(itemInfoBottomH3);

    itemInfo.appendChild(itemInfoBottom);

    item.appendChild(itemInfo);


    return item;
}

function doAjaxPost() {

    setLoadingScreen();

    const queryParameter = $("#searchbar").val().trim();
    if(queryParameter == "") {
        removeLoadingScreen();
        return;
    }


    $.ajax({
        type: "POST",
        url: "/",
        data: {"queryParameter": queryParameter},
        success: function(response){
            result.innerHTML = "";
            for(let i = 0; i < response.length; i++) {
                const URL = response[i].url;
                const photoURL = response[i].photoURL;
                const name = response[i].name;
                const price = response[i].price;
                const shop = response[i].shop;
                result.appendChild(createResultItem(URL, photoURL, name, price, shop));
            }

            if(result.innerHTML == "") {
                const p = document.createElement("p");
                p.style.color = "var(--secondary)";
                p.style.textAlign = "center";
                p.textContent = "Нет результатов";
                result.appendChild(p);
            }
            $("#searchbar")[0].scrollIntoView(true);
            removeLoadingScreen();
         },
         error: function(e){
             alert('Error: ' + e);
         }
    });
}

function setLoadingScreen() {
    $("body")[0].style.overflow = "hidden";
    $("#page-mask")[0].style.display = "block";
    $("#loading-spinner")[0].style.display = "block";
}

function removeLoadingScreen() {
    $("body")[0].style.removeProperty("overflow");
    $("#page-mask")[0].style.display = "none";
    $("#loading-spinner")[0].style.display = "none";
}


$("#searchbar-button").click(function() {
    this.blur();
    doAjaxPost();
});

$("#searchbar").keyup(function(event) {
    if(event.keyCode != 13)
        return;

    this.blur();
    doAjaxPost();
});