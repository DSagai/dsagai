/**
 * Created by Dmytro Sagai on 11.04.2017.
 */

var req;
var isIE;
var showAll;
var outputTable;
var url;




function init() {
    url= "TodoTaskServlet";
    outputTable = document.getElementById("outputTable");
    onClickShowAll();
}

function updateTodoTable() {
    var params = "command=List&showAll="+showAll;
    sendRequest(params);
}

function parseMessage(responseText) {
    // no matches returned
    if (responseText == null) {
        return false;
    } else {
        //var todoTasks = responseXML.getElementsByTagName("TodoList")[0];
        var todoTasks = JSON.parse(responseText);
        if (todoTasks.length > 0) {
            outputTable.setAttribute("bordercolor", "black");
            outputTable.setAttribute("border", "1");
            for (i = 0; i < todoTasks.length; i++){
                var task = todoTasks[i];
                addRow(task.id, task.description, task.created, task.done);
            }
        }
    }
}

function addRow(id, description, created, done) {
    var row;
    var descriptionCell;
    var createdCell;
    var doneCell;
    var doneCheckBox;

    outputTable.style.display = 'table';
    row = document.createElement("tr");

    descriptionCell = document.createElement("td");
    descriptionCell.setAttribute("class","description");
    descriptionCell.textContent = description;

    createdCell = document.createElement("td");
    createdCell.setAttribute("class","created");
    createdCell.setAttribute("timeLong",created);
    createdCell.textContent = customDateFormat(new Date(created));

    doneCell = document.createElement("td");
    doneCell.setAttribute("class","done");
    doneCheckBox = document.createElement("input");
    doneCheckBox.setAttribute("type", "checkbox");
    doneCheckBox.setAttribute("onClick","updateTask(this);");
    if (done == true) {
        doneCheckBox.setAttribute("checked", null);
    }
    doneCheckBox.setAttribute("todoId",id);
    doneCell.appendChild(doneCheckBox);
    row.appendChild(descriptionCell);
    row.appendChild(createdCell);
    row.appendChild(doneCell);
    outputTable.appendChild(row);

}

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function callback() {
    clearTable();

    if (req.readyState == 4) {
        if (req.status == 200) {
            parseMessage(req.responseText);
        }
    }
}

function clearTable() {
    if (outputTable.getElementsByTagName("tr").length > 0) {
        outputTable.style.display = "none";
        for (i = outputTable.childNodes.length - 1; i >=0; i--) {
            outputTable.removeChild(outputTable.childNodes[i]);
        }
    }
}

function onClickShowAll() {
    var showAllDefaultValue = "Show all";
    var alterValue = "Hide completed";
    var showAllElem = document.getElementById("showAll");
    if (showAllElem.textContent == showAllDefaultValue) {
        showAll = true;
        showAllElem.textContent = alterValue;
    } else {
        showAll = false;
        showAllElem.textContent = showAllDefaultValue;
    }
    updateTodoTable();
}

function addTask() {
    var description = document.getElementById("description").value;
    if (description != "") {
        var params = "command=add_update&description=" + description + "&showAll=" + showAll;
        sendRequest(params);
    }
    document.getElementById("description").value = "";
}

function updateTask(elem) {
    var id = elem.getAttribute("todoId");
    var done = elem.checked;
    var row = elem.parentElement.parentElement;

    var description = row.getElementsByClassName("description")[0].textContent;
    var created = row.getElementsByClassName("created")[0].getAttribute("timeLong");

    var params = "command=add_update&description=" + description + "&showAll=" + showAll +
        "&created=" + created + "&done=" + done + "&id=" + id;
    sendRequest(params);
}

function sendRequest(params) {
    req = initRequest();
    req.open("POST", url, true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.setRequestHeader("Content-length", params.length);
    req.setRequestHeader("Connection", "close");
    req.onreadystatechange = callback;
    req.send(params);
}


function customDateFormat(date) {
    var year = date.getFullYear();
    var mm = date.getMonth() + 1;
    var dd = date.getDay();
    var hh = date.getHours();
    var min = date.getMinutes();
    var sec = date.getSeconds();

    return [year, "-",
            mm < 10 ? "0" : "", mm, "-",
            dd < 10 ? "0" : "", dd,
            " ",
            hh < 10 ? "0" : "", hh, ":",
            min < 10 ? "0" : "", min, ":",
            sec < 10 ? "0" : "", sec
    ].join('');
}