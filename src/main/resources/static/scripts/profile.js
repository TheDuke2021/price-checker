"use strict"

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
    const password = $("#password").val();
    const confirmPassword = $("#confirmPassword").val();
    if(password != confirmPassword) {
        alert("Пароли не совпадают");
        return;
    }

    $("#change-password-form")[0].submit();
});