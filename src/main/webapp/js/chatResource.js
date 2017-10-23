  var host='http://localhost:8081/walkdog';
  var chatApiPrefix='/p1/chat';
var chatcontent='';

    function onMessageReceived(evt) {
       
        var msg = JSON.parse(evt.data); // native API

        switch(msg.action){
            case "login":
                onlineUserList.push(msg.senderid);
                $("#onlinestatus")[0].innerHTML=showOnlineUsers();
                break;
            case "logout":
                onlineUserList.pop(msg.senderid);
                $("#onlinestatus")[0].innerHTML=showOnlineUsers();
                break;
            case "message":
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
        if(onlineUserList.length>0){
            for(var i in onlineUserList){
                onlineusers+=("<button>"+onlineUserList[i]+"</button><br>")
            }
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
        $('#chatwindow').append("<tr><td>Me: </td><td>"+msg+"</td></tr>");
        window.scrollTo(0,document.body.scrollHeight);

        wsocket.send(tomsg);
    }