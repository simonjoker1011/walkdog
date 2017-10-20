  var host='http://localhost:8081/walkdog';
  var chatApiPrefix='/p1/chat';
var chatcontent='';

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
                $('#chatwindow').append("<tr><td>"+msg.senderid+": </td><td>"+msg.message+"</td></tr>");
                window.scrollTo(0,document.body.scrollHeight);
                break;
            default:

        }
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
        var tomsg = '{"message":"' + msg + '",'+
                    '"senderid":"'  + '",'+
                    '"reveiverid":"'+ receiver +'",'+
                    '"received":"'+'",'+
                    '"action":"message"'+
                    '}';

        console.log(tomsg);                 

        $('#chatwindow').append("<tr><td>Me: </td><td>"+msg+"</td></tr>");
        window.scrollTo(0,document.body.scrollHeight);

        wsocket.send(tomsg);
    }