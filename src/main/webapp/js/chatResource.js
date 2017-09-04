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