// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

async function getScript() {
    const file = await fetch("https://gist.githubusercontent.com/MattIPv4/045239bc27b16b2bcf7a3a9a4648c08a/raw/2411e31293a35f3e565f61e7490a806d4720ea7e/bee%2520movie%2520script");
    const text = await file.text();

    const scriptContainer = document.getElementById('scriptContainer');
    scriptContainer.innerText = text;

    const scriptButton = document.getElementById('scriptButton');
    scriptButton.setAttribute("onClick", "clearScript()");
    scriptButton.innerText = "Nevermind"
}

async function clearScript() {
    const scriptContainer = document.getElementById('scriptContainer');
    scriptContainer.innerText = "";

    const scriptButton = document.getElementById('scriptButton');
    scriptButton.setAttribute("onClick", "getScript()");
    scriptButton.innerText = "Get Bee Movie Script"
}

