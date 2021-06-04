window.onload = function () {

    var miliseconds=00;
    var seconds=00;
    var minutes=00;
    var min = document.getElementById("minutes");
    var sec = document.getElementById("seconds");
    var tens = document.getElementById("tens");
    var buttonStart = document.getElementById("StartTime");
    var buttonStop = document.getElementById("StopTime");
    var buttonReset = document.getElementById("ResetTime");
    var interval;

    buttonStart.onclick = function StartTime() {
        clearInterval(interval);
        interval = setInterval(ontimer, 10);
    }

    buttonStop.onclick = function StopTime() {
        clearInterval(interval);
    }

    buttonReset.onclick = function ResetTime(){
        clearInterval(interval);
        miliseconds="00";
        seconds="00";
        minutes="00";
        tens.innerHTML=miliseconds;
        sec.innerHTML=seconds;
        min.innerHTML=minutes;
    }

    function ontimer() {

        miliseconds = miliseconds + 1;

        if (miliseconds < 9) {
            tens.innerHTML = "0" + miliseconds;
        }

        if (miliseconds > 9) {
            tens.innerText = miliseconds;
        }

        if (miliseconds > 99) {
            seconds++;
            sec.innerHTML = "0" + seconds;
            miliseconds = 0;
            tens.innerHTML = "0" + 0;
        }

        if (seconds > 9) {
            sec.innerHTML = seconds;
        }

        if (seconds > 59) {
            minutes++;
            min.innerHTML = "0" + minutes;
            seconds = 0;
            sec.innerHTML = "0" + 0;
        }

        if (minutes > 9) {
            min.innerHTML = minutes;
        }
    }

    
}