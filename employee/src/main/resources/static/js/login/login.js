// login.js
$(document).ready(function() {
    $('.ad-login-member').click(function() {
        var email = $('#email').val();
        var password = $('#password').val();

        $.ajax({
            //here need to change the url
            url: "http://jobmarketplace:8090/api/auth/login",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: {
                email: email,
                password: password
            },
            success: function(response) {
                console.log(response);
                console.log("go to index!")
                window.location.href = 'index.html';
            },
            error: function(xhr, status, error) {
                console.log('1111');
                console.error("Error: " + status + " " + error);
                window.location.href = 'login.html?error=true';
            }
        });
    });
});
