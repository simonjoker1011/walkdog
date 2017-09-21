  var host='http://localhost:8081/walkdog';
  var chatApiPrefix='/p1/chat';

    $(function() {
        $("#chatbox").on("submit", function(e) {
          console.log("input: " + document.getElementById("dialog").value);
          document.getElementById("dialog").value=null;
            e.preventDefault();
            // $.ajax({
            //     url: $(this).attr("action"),
            //     type: 'POST',
            //     data: $(this).serialize(),
            //     beforeSend: function() {
            //         $("#message").html("sending...");
            //     },
            //     success: function(data) {
            //         $("#message").hide();
            //         $("#response").html(data);
            //     }
            // });
        });
    });

    function createChatroom(){
        FB.getLoginStatus(function(response){
        
        var resp = $.ajax({
                        url: host+userApiPrefix+"/createChatroom",
                        type: "POST",
                        data:{
                            userid: response.authResponse.userID
                        },
                        async: false,
                        contentType: "application/x-www-form-urlencoded",
                        dataType: 'json'
                    }).responseJSON;
        console.log("temp")
        });
    }

    $(function() {
        

        $("#createChatroom").click(function(){
            checkLoginState();

            // $.ajax({
            //     url: host+userApiPrefix+"/createChatroom",
            //     type: "POST",
            //     data:{
            //         userid:
            //     },
            //     async: false,
            //     contentType: "application/x-www-form-urlencoded",
            //     dataType: 'json'
            // }).responseJSON;
        });
    });

    $(function() {
        $("#createChatrrom").on("submit", function(e) {
          console.log("input: " + document.getElementById("dialog").value);
          document.getElementById("dialog").value=null;
            e.preventDefault();
            // $.ajax({
            //     url: $(this).attr("action"),
            //     type: 'POST',
            //     data: $(this).serialize(),
            //     beforeSend: function() {
            //         $("#message").html("sending...");
            //     },
            //     success: function(data) {
            //         $("#message").hide();
            //         $("#response").html(data);
            //     }
            // });
        });
    });