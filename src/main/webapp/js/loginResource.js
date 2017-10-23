
  var host='http://localhost:8081/walkdog';
  var userApiPrefix='/p1/user';
  var wsocket;
  var serviceLocation = "ws://localhost:8081/walkdog/chat/user/";
  var onlineUserList=[];
  var myid;

  function statusChangeCallback(response) {
    if (response.status === 'connected') {
      FB.api('/me',{fields: 'id,cover,name,first_name,last_name,age_range,link,email,picture,updated_time'}, function(response) {
        document.getElementById('status').innerHTML =
          'Thanks for logging in, ' + response.name + '!';
      });

      document.getElementById('loginlogout').innerHTML='Log out';
    } else {
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app.';

      document.getElementById('loginlogout').innerHTML='Log in with Facebook';
    }
  }

  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

  function loginlogout(){
    FB.getLoginStatus(function(response) {
          if (response.status === 'connected') {

            FB.logout(function(response) {
              statusChangeCallback(response);
            });
            wsocket.close();
            onlineUserList=[];
            $("#onlinestatus")[0].innerHTML=showOnlineUsers();
          }else{
            FB.login(function(response) {
              statusChangeCallback(response);               

              FB.api('/me',{fields: 'id,cover,name,first_name,last_name,age_range,link,email,picture,updated_time'},   function(response) {
                var resp = $.ajax({
                  type: "POST",
                  url: host+userApiPrefix,
                  data:{
                    id: response.id,
                    name: response.name,
                    firstname: response.first_name,
                    lastname: response.last_name,
                    agerange: response.age_range.min,
                    link: response.link,
                    picture: response.picture.data.url,
                    // cover: response.cover.source 
                  },
                  async: false,
                  contentType: "application/x-www-form-urlencoded",
                  dataType: 'json'
                }).responseJSON;

                wsocket = new WebSocket(serviceLocation + response.id);
                wsocket.onmessage = onMessageReceived;
                
                getOnlineUsers(response.id);
                myid=response.id;
              });
            });
          };
    });
  }

  function getOnlineUsers(userid){
                var onlineusers = $.ajax({
                    type: "GET",
                    url: host+userApiPrefix+"/online",
                    async: false,
                    contentType: "application/json"
                    // dataType: 'json'
                  }).responseJSON;
                
                for(var i in onlineusers.online){
                  if(onlineusers.online[i]!=userid){
                    onlineUserList.push(onlineusers.online[i]);
                  }
                }
                $("#onlinestatus")[0].innerHTML=showOnlineUsers();
  }

  window.fbAsyncInit = function() {
    FB.init({
      appId      : '487802971565924',
      cookie     : true,  // enable cookies to allow the server to access
                          // the session
      xfbml      : true,  // parse social plugins on this page
      version    : 'v2.8' // use graph api version 2.8
    });

    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  };

  // Load the SDK asynchronously
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));


