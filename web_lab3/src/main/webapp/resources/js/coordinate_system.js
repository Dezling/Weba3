const graph = document.getElementById("graph");
const point = document.getElementById("point");
const ctx = graph.getContext("2d");
const pointCtx = point.getContext("2d");
let rRes = document.getElementById("form:r");
const xButton = document.getElementById("form:x");
const yButton = document.getElementById("form:y");
const graphWidth = graph.clientWidth;
const graphHeight = graph.clientHeight;
const cageSize = 40;
const colorBlack = "rgb(0, 0, 0)";
const colorRed = "rgb(255, 0, 0)";
const colorCuriousBlue = "rgb(52, 152, 219)";
const colorMariner = "rgb(41, 128, 185)";
const colorCinnabar = "rgb(231, 76, 60)";
const colorTallPoppy = "rgb(192, 57, 43)";
const colorButtercup = "rgb(243, 156, 18)";
const colorBurntOrange = "rgb(211, 84, 0)";
const colorBlue = "rgb(25, 0, 255)";
const colorGreen = "rgb(0, 255, 0)";
const colorMaroon = "rgb(128, 0, 0)";
const rNullMessage = document.createElement("div");
rNullMessage.classList.add("message");
document.body.appendChild(rNullMessage);


function drawGrid() {
    const xAxis = (Math.round(graphWidth / cageSize / 2) - 1) * cageSize;
    const yAxis = Math.round(graphHeight / cageSize / 2) * cageSize;
    ctx.font = "20px fantasy";
    ctx.beginPath();
    ctx.strokeStyle = colorRed;
    for (let i = 0; i <= graphWidth; i = i + cageSize) {
        ctx.moveTo(i, 1);
        ctx.lineTo(i, graphHeight);
        ctx.fillText((i - xAxis) / cageSize, i, yAxis);
    }
    for (let i = 0; i <= graphHeight; i = i + cageSize) {
        ctx.moveTo(0, i);
        ctx.lineTo(graphWidth, i);
        ctx.fillText((yAxis - i) / cageSize, xAxis, i);
    }
    ctx.stroke();
    ctx.closePath();
    ctx.beginPath();
    ctx.strokeStyle = colorBlack;
    ctx.moveTo(xAxis, 0);
    ctx.lineTo(xAxis - 1, graphHeight);
    ctx.stroke();
    ctx.moveTo(0, yAxis);
    ctx.lineTo(graphWidth, yAxis - 1);
    ctx.stroke();
    ctx.closePath();
}

function drawGraph(r) {
    ctx.fillStyle = colorBlack;
    ctx.strokeStyle = colorBlack;
    ctx.clearRect(0, 0, graphWidth, graphHeight);
    drawGrid();
    const cage = r * cageSize;
    const centerX = (Math.round(graphWidth / cageSize / 2) - 1) * cageSize;
    const centerY = Math.round(graphHeight / cageSize / 2) * cageSize;
    square(cage, centerX, centerY);
    circle(cage, centerX, centerY);
    triangle(cage, centerX, centerY);
    ctx.fillStyle = colorBlack;
    ctx.strokeStyle = colorBlack;
    rRes.value = r;
}

function square(cage, centerX, centerY) {
    ctx.beginPath();
    ctx.rect(centerX - cage, centerY, cage, -cage);
    ctx.fillStyle = colorCuriousBlue;
    ctx.fill();
    ctx.strokeStyle = colorMariner;
    ctx.stroke();
    ctx.closePath();
}

function circle(cage, centerX, centerY) {
    ctx.beginPath();
    ctx.arc(centerX, centerY, cage, (Math.PI / 180) * 270, (Math.PI / 180) * 360);
    ctx.lineTo(centerX + cage, centerY);
    ctx.lineTo(centerX, centerY);
    ctx.fillStyle = colorButtercup;
    ctx.fill();
    ctx.strokeStyle = colorBurntOrange;
    ctx.stroke();
    ctx.closePath();
}

function triangle(cage, centerX, centerY) {
    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX, centerY + cage);
    ctx.lineTo(centerX - cage / 2, centerY);
    ctx.closePath();
    ctx.fillStyle = colorCinnabar;
    ctx.fill();
    ctx.strokeStyle = colorTallPoppy;
    ctx.stroke();
    ctx.closePath();
}

function dot() {
    const xAxis = (Math.round(graphWidth / cageSize / 2) - 1) * cageSize;
    const yAxis = Math.round(graphHeight / cageSize / 2) * cageSize;
    const xRes = xButton.value;
    const yRes = yButton.value;
    const x = xAxis + xRes * cageSize;
    const y = yAxis - yRes * cageSize;
    pointCtx.clearRect(0, 0, graphWidth, graphHeight);
    pointCtx.beginPath();
    pointCtx.arc(x, y, 3, 0, 2 * Math.PI);
    pointCtx.fillStyle = colorBlue;
    pointCtx.fill();
    pointCtx.closePath();
    yButton.value = yRes;
}
function dotForList(x, y, result) {
    const xAxis = (Math.round(graphWidth / cageSize / 2) - 1) * cageSize;
    const yAxis = Math.round(graphHeight / cageSize / 2) * cageSize;
    const xRes = xAxis + x * cageSize;
    const yRes = yAxis - y * cageSize;
    pointCtx.beginPath();
    pointCtx.arc(xRes, yRes, 3, 0, 2 * Math.PI);
    if (result === true) {
        pointCtx.fillStyle = colorGreen;
    } else {
        pointCtx.fillStyle = colorMaroon;
    }

    pointCtx.fill();
    pointCtx.closePath();
}
window.addEventListener('DOMContentLoaded', allDots);

function allDots(){
    const dataJson = document.getElementById('pointList').value;
    const data = JSON.parse(dataJson);
    data.forEach(point => {
        console.log(point);
        dotForList(point.x, point.y, point.result);
})
}

point.addEventListener("click", handleClick);

function handleClick(event) {
    if (!rRes.value) {
        rNullMessage.innerText = "Без R не работает)" + rRes.value;
        rNullMessage.style.display = "block";
        setTimeout(() => {
            rNullMessage.style.display = "none";
        }, 2000);
    } else {
        const xAxis = (Math.round(graphWidth / cageSize / 2) - 1) * cageSize;
        const yAxis = Math.round(graphHeight / cageSize / 2) * cageSize;
        const rect = graph.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;
        const xRes = (x - xAxis) / cageSize;
        const yRes = (yAxis - y) / cageSize;
        xButton.value = xRes;
        yButton.value = yRes;
        console.log(xButton.value, yButton.value, "down", yButton, xButton);
        dot();
    }
}

window.addEventListener("DOMContentLoaded", drawGrid);
