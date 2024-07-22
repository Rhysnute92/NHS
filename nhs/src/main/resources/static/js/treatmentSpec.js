var coll = document.getElementsByClassName("collapsible_button");
for (var i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.display === "block") {
      content.style.display = "none";
    } else {
      content.style.display = "block";
    }
  });
}

let gallery = document.getElementById("display_item").getElementsByTagName("img");
function backClick(){
  for (var i = 0; i < gallery.length; i++){
    if (gallery[i].style.display === "inline-block"){
      gallery[i].style.display = "none";
      if (i === 0){
        gallery[gallery.length-1].style.display = "inline-block";
      } else {
        gallery[i-1].style.display = "inline-block";
      }
      break;
    }
  }
}
function forwardClick(){
  for (var i = 0; i < gallery.length; i++){
    if (gallery[i].style.display === "inline-block"){
      gallery[i].style.display = "none";
      if (i === gallery.length-1){
        gallery[0].style.display = "inline-block";
      } else {
        gallery[i+1].style.display = "inline-block";
      }
      break;
    }
  }
}


