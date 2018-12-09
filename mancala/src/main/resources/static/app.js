var stompClient = null;

function createPitsTopRow(){
    var docFrag = document.createDocumentFragment();
    for (var i=0; i < 5 ; i++){
     var elem = document.createElement('input');
     elem.type = 'button';
     if(i==6)
        elem.className='btn btn-custom';
     else
     {
        elem.className='btn btn-success';
        elem.onclick=performMove(i);
     } 
     docFrag.appendChild(elem);
}
document.body.getElementsByTagName("div")[10].appendChild(docFrag);
}

function createPitsBottomRow(){
    var docFrag = document.createDocumentFragment();
    for (var i=7; i < 13 ; i++){
     var elem = document.createElement('input');
     elem.type = 'button';
     if(i==13)
        elem.className='btn btn-custom';
     else{
        elem.className='btn btn-success';
        elem.onclick=performMove(i);
     }  
     docFrag.appendChild(elem);
}
document.body.appendChild(docFrag);
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/game',function(message) {
        	drawGameBoard(JSON.parse(message.body));
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function startGame() {
    stompClient.send("/app/startGame", {}, {});
}

function performMove(boardPitID) {
    stompClient.send("/app/performMove", {}, boardPitID);
}

function drawGameBoard(message) {
    $("#gameStatus").text(message.gameStatus);
    $("#winner").text(message.winner);
    piles = message.piles;
    for (i=0; i<14; i++) {
    	$("#"+i).text(piles[i]);
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#start" ).click(function() { startGame(); });
});