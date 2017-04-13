/**
 * Created by Dmytro Sagai on 11.04.2017.
 */

var req;
var isIE;
var showAll;
var outputTable;




function init() {
    outputTable = document.getElementById("outputTable");
    onClickShowAll();
}

function updateTodoTable() {
    var url = "TodoTaskServlet";
    var params = "command=List&showAll="+showAll;
    req = initRequest();
    req.open("POST", url, true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.setRequestHeader("Content-length", params.length);
    req.setRequestHeader("Connection", "close");
    req.onreadystatechange = callback;
    req.send(params);
}

function parseMessage(responseXML) {
    // no matches returned
    if (responseXML == null) {
        return false;
    } else {
        var todoTasks = responseXML.getElementsByTagName("TodoList")[0];
        if (todoTasks.childNodes.length > 0) {
            outputTable.setAttribute("bordercolor", "black");
            outputTable.setAttribute("border", "1");
            for (i = 0; i < todoTasks.childNodes.length; i++){
                var task = todoTasks.childNodes[i];
                var id = task.getAttribute("id");
                var description = task.getAttribute("description");
                var created = task.getAttribute("created");
                var done = task.getAttribute("done");
                addRow(id, description, created, done);
            }
        }
    }
}

function addRow(id, description, created, done) {
    var row;
    var descriptionCell;
    var createdCell;
    var doneCell;



    outputTable.style.display = 'table';
    row = document.createElement("tr");
    descriptionCell = document.createElement("td");
    descriptionCell.appendChild(document.createTextNode(description));
    createdCell = document.createElement("td");
    createdCell.appendChild(document.createTextNode(created));
    doneCell = document.createElement("td");
    doneCell.appendChild(document.createTextNode(done));
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
            parseMessage(req.responseXML);
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
        showAll = false;
        showAllElem.textContent = alterValue;
    } else {
        showAll = true;
        showAllElem.textContent = showAllDefaultValue;
    }
    updateTodoTable();
}