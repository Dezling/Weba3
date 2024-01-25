const dataY = document.getElementById("form:y");
const xCoo = document.getElementById("form:x");
const r = document.getElementById("form:r");
function setY(y) {
  dataY.value = y;
}
window.onload = function() {
    document.getElementById("form:x").value = "";
    document.getElementById("form:y").value = "";
    const radios = document.getElementsByName("form:r");
    for (let i = 0; i < radios.length; i++) {
      radios[i].checked = false;
    }
  };