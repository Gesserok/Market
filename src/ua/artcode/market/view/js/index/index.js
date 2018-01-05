var loginButton = $('#loginButton');



$(document).ready(function() {
    loginButton.click(function () {

        $.ajax({
            type: 'POST',
            url: '/login',
            data: JSON.stringify({"login": $('#inputLogin').val(), "password": $('#password').val()}),
            dataType: 'json',
            success: function(){
                // answer.token;
                hideLoginForm('true');
            },
            error: function(){
                alert('Login or password fail!');
            }
        });

    });

    $.cachedScript = function( url, options ) {

        // Allow user to set any option except for dataType, cache, and url
        options = $.extend( options || {}, {
            dataType: "script",
            cache: true,
            url: url
        });

        // Use $.ajax() since it is more flexible than $.getScript
        // Return the jqXHR object so we can chain callbacks
        return $.ajax( options );
    };

});
