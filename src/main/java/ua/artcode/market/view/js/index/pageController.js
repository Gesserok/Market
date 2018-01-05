
    function showAllUserFunction() {

        var userForm = $('<form id="userFunction"></form>').attr({
            display: true
        }).appendTo("body");

        var divCreateBill = userForm.append('<div id="createBill"></div>');
        divCreateBill.append('<span id="spanCreateBill"></span>').append('<button id="buttonCreateBill" class="btn btn-default">CreateBill</button>');

        var divFindBill = userForm.append('<div id="findBill"></div>');
        divFindBill.append('<span id="spanFindBill"></span>').append('<button id="buttonFindBill" class="btn btn-default">FindBill</button>');
        $('#spanFindBill').append('<input id="billId" type="text" placeholder="Enter bill ID"/>');

        var divFindProduct = userForm.append('<div id="findProduct"></div>');
        divFindProduct.append('<span id="spanFindProduct"></span>').append('<button id="buttonFindProduct" class="btn btn-default">FindProduct</button>');
        $('#spanFindProduct').append('<input id="productId" type="text" placeholder="Enter product ID"/>');

        var divCreateProduct = userForm.append('<div id="creatProduct"></div>');
        divCreateProduct.append('<span id="spanCreateProduct"></span>').append('<button id="buttonCreateProduct" class="btn btn-default">CreateProduct</button>');
        $('#spanCreateProduct').append('<input id="productName" type="text"placeholder="Enter product name"/>');
        $('#spanCreateProduct').append('<input id="productPrice" type="text" placeholder="Enter product price"/>');

        var divLogout = userForm.append('<div id="logout"></div>');
        divLogout.append('<span id="spanLogout"></span>').append('<button id="buttonLogout" class="btn btn-default">Logout</button>');



        $("<script src='js/index/billModel.js' async></script>").appendTo("body");

        // $.cachedScript( "js/index/billModel.js" ).done(function( script, textStatus ){})


    }

    function showBillFunction() {

       var userForm = $('<form id="billFunction"></form>').attr({
        display: true
       }).appendTo("body");

       var divAddProduct = userForm.append('<div id="addProduct"></div>');
       divAddProduct.append('<span id="spanAddProduct"></span>').append('<button id="buttonAddProduct" class="btn btn-default">AddProduct</button>');
       $('#spanAddProduct').append('<input id="producteId" type="text" placeholder="Enter product ID"/>');

    }



    function hideLoginForm(t) {
        if (t === 'true') {

            $('#loginForm').hide();
            showAllUserFunction();
        }
    }

    function hideUserFunction(t) {
        if (t === 'true') {

            $('#userFunction').hide();
            showBillFunction();
        }
    }