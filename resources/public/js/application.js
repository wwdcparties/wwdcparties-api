function filterElementsArray() {
    var filterElements = document.getElementsByClassName('js-filter');
    return Array.prototype.slice.call(filterElements);
}
function listingElementsArray() {
    var listingElements = document.getElementsByClassName('event-listing');
    return Array.prototype.slice.call(listingElements);
}
function daysArray() {
    var dayElements = document.getElementsByClassName('day');
    return Array.prototype.slice.call(dayElements);
}
function dayHasVisibleChildren(day) {
    var eventsChildren = day.getElementsByClassName('event-listing');
    var children = Array.prototype.slice.call(eventsChildren);
    var visibleChildren = children.filter(function (element) { return element.style.display !== 'none'; });
    return visibleChildren.length > 0;
}
function activeFilters() {
    return filterElementsArray().filter(function (element) { return element.checked; }).map(function (element) { return element.getAttribute('data-filter'); });
}
function applyFilters() {
    //hide all elements that don't match filter
    var filters = activeFilters();
    listingElementsArray().forEach(function (element) {
        var elementClasses = element.className.split(' ');
        var matchingClasses = elementClasses.filter(function (elementClass) { return filters.indexOf(elementClass) != -1; });
        var matchesFilters = matchingClasses.length == filters.length;
        element.style.display = (matchesFilters ? '' : 'none');
    });
    //hide all empty days
    var days = daysArray();
    days.forEach(function (day) {
        day.style.display = (dayHasVisibleChildren(day) ? '' : 'none');
    });
    //update URL for linking	
    var filtersParam = filters.join(',');
    if (filtersParam) {
        window.history.replaceState(null, "", window.location.pathname + "?filter=" + filtersParam);
    }
    else {
        window.history.replaceState(null, "", window.location.pathname);
    }
}
function setFiltersFromParam(filtersParam) {
    var filters = filtersParam.split(',');
    var filterElements = filterElementsArray();
    filterElements.forEach(function (element) {
        var elementFilter = element.getAttribute('data-filter');
        element.checked = (filters.indexOf(elementFilter) !== -1);
    });
}
function getQueryString(field, url) {
    var href = url ? url : window.location.href;
    var reg = new RegExp('[?&]' + field + '=([^&#]*)', 'i');
    var string = reg.exec(href);
    return string ? string[1] : null;
}
window.addEventListener('load', function (event) {
    setFiltersFromParam(getQueryString('filter'));
    applyFilters();
});
//# sourceMappingURL=application.js.map