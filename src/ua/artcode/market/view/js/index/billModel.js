
var buttonCreateBill = $('#buttonCreateBill');
var buttonFindBill = $('#buttonFindBill');
var buttonFindProduct = $('#buttonFindProduct');
var buttonCreateProduct = $('#buttonCreateProduct');
var buttonLogout = $('#buttonLogout');

$(document).ready(function() {

    buttonCreateBill.click(function () {

        $.ajax({
            type: 'POST',
            url: '/employee/bill',
            data: JSON.stringify({"bill": 'newBill'}),
            dataType: 'json',
            success: function(){
            // answer.token;
            hideUserFunction('true');
            },
            error: function(){
                alert('NONONONONONONNO!');
            }
        });
    });

    buttonFindProduct.click(function() {
        var jqxhr = $.get('/employee/product?id='.concat($('#productId').val()), function () {
        }).done(function (data) {
            if (data === 'ua.artcode.market.exclude.exception.ProductNotFoundException') {
                alert('Product not found');
            } else {
                hideUserFunction('true');
            }
        }).
        error(function(data) {
            // alert( data );
        })
    });

    buttonFindBill.click(function() {
        var jqxhr = $.get('/employee/bill?id='.concat($('#billId').val()), function () {

        }).done(function (data) {
            if (data === 'ua.artcode.market.exclude.exception.BillNotFoundException') {
                alert('Bill not found');
            } else {
                hideUserFunction('true');
            }
        }).
        error(function(data) {
            alert( data );
        })
    });

    // var money = function (price) {
    //     if (price.contains('.')) {
    //         JSON.stringify({
    //             'name': $('#productName').val(), 'moneyWholePart': $('#productPrice').val().split('.')[0],
    //             'moneyFraction': $('#productPrice').val().split('.')[1]
    //         })
    //     }
    //     else if (price.contains(',')) {
    //         JSON.stringify({
    //             'name': $('#productName').val(), 'moneyWholePart': $('#productPrice').val().split('.')[0],
    //             'moneyFraction': $('#productPrice').val().split(',')[1]
    //         })
    //     }
    //     else {
    //         returnJSON.stringify({
    //             'name': $('#productName').val(), 'moneyWholePart': $('#productPrice').val(),
    //             'moneyFraction': '0'
    //         })
    //     }
    // };

    buttonCreateProduct.click(function () {

        $.ajax({
            type: 'POST',
            url: '/employee/product',
            data: JSON.stringify({'name': $('#productName').val(), 'moneyWholePart': $('#productPrice').val().split('.')[0],
                'moneyFraction': $('#productPrice').val().split('.')[1]}),
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

    buttonLogout.click(function() {
        var jqxhr = $.get('/logout', function () {

        }).done(function (data) {
            alert('logout')
        }).
        error(function(data) {
            alert( data );
        })
    });


});