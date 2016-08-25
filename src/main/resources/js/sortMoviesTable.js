
var query = parseQuery(location.search);

function parseQuery(qstr) {
    var query = {};
    var a = qstr.substr(1).split('&');
    for (var i = 0; i < a.length; i++) {
        var b = a[i].split('=');
        query[decodeURIComponent(b[0])] = decodeURIComponent(b[1] || '');
    }
    return query;
}

function sortTable(selected) {
    //invert sort
    var key = 'sortBy';
    var sortValue = query[key];
    var suffix = 'Desc';
    if(sortValue === selected)
        sortValue = sortValue.concat(suffix)
    else
    if(sortValue == selected.concat(suffix))
        sortValue = sortValue.replace(suffix,'')
    else
        sortValue = selected
    query[key] = sortValue;
    //build new query string and assign
    var protocol = window.location.protocol;
    var host = window.location.hostname;
    var path = window.location.pathname;
    var queryString = '?';
    for (var i in query){
        if(i != undefined && i != '')
            queryString = queryString.concat(i).concat('=').concat(query[i]).concat('&');
    }
    queryString = queryString.slice(0, -1);
    window.location = path + queryString;
}
