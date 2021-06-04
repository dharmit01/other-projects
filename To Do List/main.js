/*// Create a "close" button and append it to each list item
var myNodelist = document.getElementsByTagName("LI");
var i;
for (i = 0; i < myNodelist.length; i++) {
  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  myNodelist[i].appendChild(span);
}

// Click on a close button to hide the current list item
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
  close[i].onclick = function() {
    var div = this.parentElement;
    div.style.display = "none";
  }
}

// Create a new list item when clicking on the "Add" button
function newElement() {
  var li = document.createElement("li");
  var inputValue = document.getElementById("myInput").value;
  var t = document.createTextNode(inputValue);
  li.appendChild(t);
  if (inputValue === '') {
    alert("You must write something!");
  } else {
    document.getElementById("myUL").appendChild(li);
  }
  document.getElementById("myInput").value = "";

  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  li.appendChild(span);

  for (i = 0; i < close.length; i++) {
    close[i].onclick = function() {
      li.parentNode.removeChild(li);
    }
  }
}*/

let addButton = document.querySelector(".addBtn");
let addInput = document.querySelector("#myInput");

addButton.addEventListener("click",function(){
  let  newItem = document.getElementById("myInput").value;
  if(newItem) { 
    newElement(newItem);
    document.getElementById("myInput").value = "";
  }
});

addInput.addEventListener("keypress",function(e){
  if (13 === e.keyCode){
    let newItem = document.getElementById("myInput").value;
    if (newItem) {
      newElement(newItem);
      document.getElementById("myInput").value = "";
    }
  }
});

function newElement(){
  var input = document.getElementById('myInput').value;
  var node = document.createElement("LI");
  var textnode = document.createTextNode(input);
  if(input=== ''){
      alert("You must Write Something!!");
  }
  else{
    node.appendChild(textnode);
    document.getElementById('myUL').appendChild(node);

    var removeTask = document.createElement('input');
    removeTask.setAttribute('type', 'button');
    removeTask.setAttribute("value", 'x');
    removeTask.setAttribute("class","close");
    node.appendChild(removeTask);
  }

  document.getElementById("myInput").value = "";

  removeTask.addEventListener('click', function(e) {
        node.parentNode.removeChild(node);
  });
  
}