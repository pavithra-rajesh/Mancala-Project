var stompClient = null;

function createPits(){
    var button;
    for (var i=5; i >= 0 ; i--){
        button='<button id="'+i+'" class="btn btn-success custom" onclick="performMove('+i+')">'+0+'</button>';
        $("#topRow").append(button);
    }

    for (var j=7; j <= 12 ; j++){
        button='<button id="'+j+'" class="btn btn-warning custom" onclick="performMove('+j+')">'+0+'</button>';
        $("#bottomRow").append(button);
    }
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#start").prop("disabled", !connected);
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
    if(message.gameOver){
        $("#gameStatus").text("Game Over. ");
        $("#winner").text(message.winnerString);
    }
    else if (message.turnPlayerA){
        $("#gameStatus").text("Player A's turn");
    }
    else if(!message.turnPlayerA)
        $("#gameStatus").text("Player B's turn");

    boardPitsStones = message.boardPitsStones;
    for (i=0; i<14; i++) {
    	$("#"+i).text(boardPitsStones[i]);
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