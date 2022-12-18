"use strict"


const result = $(".result")[0];



function getItems() {

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
                let price = response[i].price;
                if(price == APPLICATION.null_price)
                    price = "Нет цены";
                else
                    price = price.toLocaleString('en-US').replaceAll(",", " ") + "р";
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
    getItems();
});

$("#searchbar").keyup(function(event) {
    if(event.keyCode != 13)
        return;

    this.blur();
    getItems();
});