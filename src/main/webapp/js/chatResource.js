  var host='http://localhost:8081/walkdog';
  var chatApiPrefix='/p1/chat';

    function onMessageReceived(evt) {
       
        var msg = JSON.parse(evt.data); // native API
        console.log(msg);

        switch(msg.action){
            case "login":
                onlineUserList[msg.senderid]=msg.senderid;
                $("#onlinestatus")[0].innerHTML=showOnlineUsers();
                // document.getElementById("onlinestatus").innerHTML = showOnlineUsers();
                break;
            case "logout":
                delete onlineUserList[msg.senderid];
                $("#onlinestatus")[0].innerHTML=showOnlineUsers();
                // document.getElementById("onlinestatus").innerHTML = showOnlineUsers();
                break;
            case "message":
                console.log("Received message");
                break;
            default:

        }
        // var $messageLine = $('<tr><td class="received">' + msg.received
        //         + '</td><td class="user label label-info">' + msg.sender
        //         + '</td><td class="message badge">' + msg.message
        //         + '</td></tr>');
        // $chatWindow.append($messageLine);
    }

    $(document).ready(function() {
        $('#chatbox').submit(function(evt) {
            evt.preventDefault();
            doSendMessage($('#message')[0].value,$('#receiver')[0].value);
            $('#message')[0].value='';                        
        });
    });

    function showOnlineUsers(){
        var onlineusers="";
        for(var i in onlineUserList){
            onlineusers+=(i+"<br>")
        }
        return onlineusers;
    }

    function doSendMessage(msg, receiver){
        var msg = '{"message":"' + msg + '",'+
                    '"senderid":"'  + '",'+
                    '"reveiverid":"'+ receiver +'",'+
                    '"received":"'+'",'+
                    '"action":"message"'+
                    '}';

        console.log(msg);                    
        wsocket.send(msg);
    }