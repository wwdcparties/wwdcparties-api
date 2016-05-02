function filterElementsArray() {
    var filterElements = document.getElementsByClassName('js-filter');
    return Array.prototype.slice.call(filterElements);
}
function listingElementsArray() {
    var listingElements = document.getElementsByClassName('event-listing');
    return Array.prototype.slice.call(listingElements);
}
function activeFilters() {
    return filterElementsArray().filter(function (element) { return element.checked; }).map(function (element) { return element.getAttribute('data-filter'); });
}
function applyFilters() {
    var filters = activeFilters();
    listingElementsArray().forEach(function (element) {
        var elementClasses = element.className.split(" ");
        var matchingClasses = elementClasses.filter(function (elementClass) { return filters.indexOf(elementClass) != -1; });
        var matchesFilters = matchingClasses.length == filters.length;
        element.style.display = (matchesFilters ? "list-item" : "none");
    });
}
//# sourceMappingURL=application.js.map