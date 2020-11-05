const clockTitle = document.querySelector(".clock");

function getTime() {
  const date = new Date();
  const minutes = date.getMinutes();
  const hours = date.getHours();
  const seconds = date.getSeconds();
  // clockTitle.innerText = `${hours < 10 ? `0${hours}` : hours}:${
  //   minutes < 10 ? `0${minutes}` : minutes
  // }:${seconds < 10 ? `0${seconds}` : seconds}`;
  const setAmPm = hours > 12 ? hours - 12 : hours;
  const ampm = hours >= 12 ? "PM" : "AM";

  clockTitle.innerText = `${ampm} ${setAmPm < 10 ? `0${setAmPm}` : setAmPm}:${minutes < 10 ? `0${minutes}` : minutes}:${
    seconds < 10 ? `0${seconds}` : seconds
  }`;
}

function init() {
  getTime();
  setInterval(getTime, 1000);
}

init();
