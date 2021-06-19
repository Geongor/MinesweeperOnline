var itemSelected = "none";
let isUsed = false;
    $( document ).ready(function() {

        $.ajax ({
                        url: "field/new",
                        type: "POST",
                        data: $("#field_param").serialize(),
                        dataType: "html",
                        success: rebuildField
                    });

        function funcSuccess (response) {
            var cells = response;
            for (key in cells) {
                if (key == "status" && cells[key] == "win"){
                    $("#gameTime").html(cells["gameTime"]);
                    $(".game-field").unbind("mousedown");
                    $("#status").html("победили");
                    $("#zatemnenie").css("display", "block");

                    clearInterval(timer);
                    timer = null;
                    $("#status").html("победили")
                    $("#zatemnenie").css("display", "block")
                    $("#experience").html(cells['experience']);
                } else if (key == "status" && cells[key] == "lose"){
                    $("#gameTime").html(cells["gameTime"]);
                    $(".game-field").unbind("mousedown");
                    $("#status").html("проиграли")
                    $("#zatemnenie").css("display", "block")

                    clearInterval(timer);
                    timer = null;
                    $("#experience").html(cells['experience']);
                } else {
                    $("#" + key).removeClass("cell-closed").addClass(cells[key]);
                }
            }
            if(response['chanceUsed']){
                $('.alert-success').show("fast");
                setTimeout(hideSavedAlert, 5000);
            }
        }

        function rebuildField (response) {
            var size = JSON.parse(response);
            var width = size.width;
            var height = size.height;
            $(".game-field").html(function(index, oldHtml){
                var result = "";
                for (var i = 0; i < height; i++){
                    result += "<div class='field-row'>"
                    for (var j = 0; j < width; j++){
                        var cell_id = "cell" + i + "_" + j;
                        result += "<div class='field-cell cell-closed' id=" + cell_id +
                                " data-x=" + j + " data-y=" + i + "></div>"
                    }
                    result += "</div>";
                }
                return result;
            });
        }

        function updateField(response) {

            var cells = response;
            for (key in cells){
                if (key == "locator") {
                    $("#locator").html(cells[key]);
                } else {
                    $("#" + key).toggleClass(cells[key], true);
                    $("#" + key).toggleClass("cell-closed");
                }
            }

        }

        function selectChance(responce) {
            for (key in responce){
                if (key == "chance"){
                    $("#chance").html(responce[key]);
                } else {
                    $(key).toggleClass(responce[key], true);
                }
            }
        }

        $(".game").on("mousedown", ".cell-closed" ,function (e){

            var dataObj = new Object();
            dataObj.id = $(this).attr("id");
            dataObj.x = $(this).data("x");
            dataObj.y = $(this).data("y");
            if (itemSelected == "locator"){
                $.ajax ({
                    url: "field/item/locator",
                    type: "POST",
                    data: dataObj,
                    dataType: "JSON",
                    success: updateField
                });
            } else if (e.button == 2){
                $(this).toggleClass("cell-flag cell-closed");
            } else if (e.button == 0){
                $.ajax ({
                            url: "field/cell",
                            type: "POST",
                            data: dataObj,
                            dataType: "JSON",
                            success: funcSuccess
                });
                if(!timer) {
                    timer = setInterval(setTime, 1000);
                }
            }
        });

        $(".game").on("mousedown", ".cell-flag" ,function (e){
            if (e.button == 2){
                $(this).toggleClass("cell-flag cell-closed");
            }


        });

        $("#field_param").on("mousedown", "#param_btn" ,function (e){

            minutesLabel.innerText = "00";
            secondsLabel.innerText = "00";
            totalSeconds = 0;
            $.ajax ({
                url: "field/new",
                type: "POST",
                data: $("#field_param").serialize(),
                dataType: "html",
                success: function (response) {
                    isUsed = false;
                    rebuildField(response);
                    $('.bonus').removeClass('active');
                }
            });

        });

        $(".item-locator").on("mousedown", function (e) {
            if (itemSelected == "locator") itemSelected = "none"
            else itemSelected = "locator";
        })
        $(".item-chance").on("mousedown", function (e) {
            // $(".item-chance").unbind("mousedown");
            if (!isUsed) {
                $.ajax({
                    url: "field/item/chance",
                    type: "POST",
                    dataType: "JSON",
                    success: selectChance
                });
                isUsed = true;
            }
        })

        $(".game").on({
            mouseenter: function () {
                if (itemSelected == "locator"){
                    var x = $(this).attr("data-x");
                    var y = $(this).attr("data-y");
                    for (var i = y-1; i < Number(y)+2; i++){
                        for (var j = x-1; j < Number(x)+2; j++){
                            $("#cell" + i + "_" + j).toggleClass("cell-selected");
                        }
                    }
                }
            },
            mouseleave: function () {
                if (itemSelected == "locator"){
                    var x = $(this).attr("data-x");
                    var y = $(this).attr("data-y");
                    for (var i = y-1; i < Number(y)+2; i++){
                        for (var j = x-1; j < Number(x)+2; j++){
                            $("#cell" + i + "_" + j).toggleClass("cell-selected");
                        }
                    }
                }
            }
        }, ".field-cell");

        $(".close").on("mousedown", function (e) {
            $("#zatemnenie").css("display", "none")
        })

        document.oncontextmenu = function() {return false;};

    });

let bombCount = $('#count');
let fieldWidth = $('#width');
let fieldHeight = $('#height');
let maxBombCount;


fieldHeight.on('keyup change', function() {
    maxBombCount = (parseInt(fieldWidth.val()) * parseInt(fieldHeight.val())) -1;
    bombCount.attr('max', maxBombCount);
    if($(this).val() > 80){
        $(this).val(80);
    }
});

fieldWidth.on('keyup change', function() {
    maxBombCount = (parseInt(fieldWidth.val()) * parseInt(fieldHeight.val())) -1;
    bombCount.attr('max', maxBombCount);
    if($(this).val() > 80){
        $(this).val(80);
    }
});

bombCount.on('keyup change', function() {
    maxBombCount = ((parseInt(fieldWidth.val()) * parseInt(fieldHeight.val())) -1) > 999 ? 999 :
                                    ((parseInt(fieldWidth.val()) * parseInt(fieldHeight.val())) -1);
    if($(this).val() > maxBombCount){
        $(this).val(maxBombCount);
    }
});

$('.bonus div:not(".item-chance")').click(function () {
    $(this).parent().toggleClass('active');
});
$('.item-chance').click(function () {
    $(this).parent().addClass('active').removeClass('cell-selected');
});
$('.alert-success').hide();

function hideSavedAlert() {
    $('.alert-success').hide("slow");
}


//Timer
let timer = null;
let minutesLabel = document.getElementById("minutes");
let secondsLabel = document.getElementById("seconds");
let totalSeconds = 0;


function setTime() {
    ++totalSeconds;
    secondsLabel.innerHTML = pad(totalSeconds % 60);
    minutesLabel.innerHTML = pad(parseInt(totalSeconds / 60));
}

function pad(val) {
    let valString = val + "";
    if (valString.length < 2) {
        return "0" + valString;
    } else {
        return valString;
    }
}

//shop
$('input[name="amount"]').on('keyup change', function() {
    let price =  $(this).parent('form').children('label').children('span.price').text();
   if($(this).val() < 1) {
       $(this).val(1);
       $(this).parent('form').children('label').children('span.amount').text(price);
   }
   else {
       $(this).parent('form').children('label').children('span.amount').text(price * $(this).val());
   }
});