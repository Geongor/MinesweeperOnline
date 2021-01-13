    $( document ).ready(function() {

        $.ajax ({
                        url: "new_field",
                        type: "POST",
                        data: $("#field_param").serialize(),
                        dataType: "html",
                        success: rebuildField
                    });

        function funcSuccess (response) {
            var cells = JSON.parse(response);
            for (key in cells) {
                if (key == "status" && cells[key] == "win"){
                    $("#gameTime").html(cells["gameTime"]);
                    $(".game-field").unbind("mousedown");
                    alert("You WIN!!!");
                } else if (key == "status" && cells[key] == "lose"){
                    $("#gameTime").html(cells["gameTime"]);
                    $(".game-field").unbind("mousedown");
                    alert("You LOSE!!!");
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

        $(".game-field").on("mousedown", ".cell-closed" ,function (e){
            if (e.button == 2){
                $(this).toggleClass("cell-flag");
            } else {
                $.ajax ({
                            url: "cell",
                            type: "POST",
                            data: "id=" + $(this).attr("id") + "&x=" + $(this).data("x") + "&y=" + $(this).data("y"),
                            dataType: "html",
                            success: funcSuccess
                        });

            }
        });

        $("#field_param").on("mousedown", "#param_btn" ,function (e){

            $.ajax ({
                url: "new_field",
                type: "POST",
                data: $("#field_param").serialize(),
                dataType: "html",
                success: rebuildField
            });

        });

        document.oncontextmenu = function() {return false;};

    });




