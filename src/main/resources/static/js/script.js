    var itemSelected = "none";
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
                    $("#status").html("победили")
                    $("#zatemnenie").css("display", "block")
                } else if (key == "status" && cells[key] == "lose"){
                    $("#gameTime").html(cells["gameTime"]);
                    $(".game-field").unbind("mousedown");
                    $("#status").html("проиграли")
                    $("#zatemnenie").css("display", "block")
                } else {
                    $("#" + key).removeClass("cell-closed").addClass(cells[key]);
                }
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
                $("#" + key).toggleClass(cells[key], true);
                $("#" + key).toggleClass("cell-closed");
            }

        }

        function selectChance(responce) {
            for (key in responce){
                $(key).toggleClass(responce[key], true);
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

            }
        });

        $(".game").on("mousedown", ".cell-flag" ,function (e){
            if (e.button == 2){
                $(this).toggleClass("cell-flag cell-closed");
            }


        });

        $("#field_param").on("mousedown", "#param_btn" ,function (e){

            $.ajax ({
                url: "field/new",
                type: "POST",
                data: $("#field_param").serialize(),
                dataType: "html",
                success: rebuildField
            });

        });

        $(".item-locator").on("mousedown", function (e) {
            if (itemSelected == "locator") itemSelected = "none"
            else itemSelected = "locator";

        })
        $(".item-chance").on("mousedown", function (e) {
            $(".item-chance").unbind("mousedown");
            $.ajax ({
                url: "field/item/chance",
                type: "POST",
                dataType: "JSON",
                success: selectChance
            });
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




