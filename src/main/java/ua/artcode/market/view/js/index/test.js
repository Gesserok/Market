
var buttonCreateProduct = $('#buttonCreateProduct');


var money = function (price) {
    if (price instanceof String) {

        if (price.contains('.')) {
            return JSON.stringify({
                'name': $('#productName').val(), 'moneyWholePart': $('#productPrice').val().split('.')[0],
                'moneyFraction': $('#productPrice').val().split('.')[1]
            })
        }
        else if (price.contains(',')) {
            return JSON.stringify({
                'name': $('#productName').val(), 'moneyWholePart': $('#productPrice').val().split('.')[0],
                'moneyFraction': $('#productPrice').val().split(',')[1]
            })
        }
        else {
            return JSON.stringify({
                'name': $('#productName').val(), 'moneyWholePart': $('#productPrice').val(),
                'moneyFraction': '0'
            })
        }
    }
};

buttonCreateProduct.click(function () {

    $.ajax({
        type: 'POST',
        url: '/employee/product',
        data: ($('#productPrice').val()),
        dataType: 'json',
        success: function(){
            // answer.token;
            alert('added')
        },
        error: function(){
            alert('NONONONONONONNO!');
        }
    });

});