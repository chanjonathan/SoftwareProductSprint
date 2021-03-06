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


window.onload=function(){
    handleSortForm();
}

async function getResponse() {
    const responseContainer = document.getElementById("responseContainer");

    var fetchedJSON = await fetch("/text-form-handler");
    var list = await fetchedJSON.json();

    while (list.length == 0) {
        fetchedJSON = await fetch("/text-form-handler");
        list = await fetchedJSON.json();
    }

    let string = "";

    for (i = 0; i < list.length; i++) {
        if (string.length == 0) {
            string = string.concat(list[i]);
        } else {
            string = string.concat(", ", list[i]);
        }
    }

    responseContainer.innerText = string;
}


async function handleSortForm() {
    var textForm = document.getElementById("textForm");
    
    document.getElementById("textFormButton").addEventListener("click", function () {
        textForm.submit();

        getResponse();
    });
}
