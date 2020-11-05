const greetForm = document.querySelector(".greet_form");
const greetInput = greetForm.querySelector("input");
const hello = document.querySelector(".hellomsg");

const USER = "currentUser";

function saveName() {
  localStorage.setItem(USER, greetInput.value);
}

function handleSubmit(e) {
  e.preventDefault();
  paintName(greetInput.value);
  saveName();
}

function askForName() {
  greetForm.classList.remove("hiding");
  greetForm.addEventListener("submit", handleSubmit);
}

function paintName(text) {
  greetForm.classList.add("hiding");
  hello.classList.remove("hiding");
  hello.innerText = `${text}'s to do list`;
}

function loadName() {
  const loadedName = localStorage.getItem(USER);
  if (loadedName !== null) {
    paintName(loadedName);
  } else {
    askForName();
  }
}

function init() {
  loadName();
}

init();
