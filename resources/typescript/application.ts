function filterElementsArray() :HTMLInputElement[] {
	var filterElements = document.getElementsByClassName('js-filter');
	return Array.prototype.slice.call(filterElements);
}

function listingElementsArray() :HTMLLIElement[] {
	var listingElements = document.getElementsByClassName('event-listing');
	return Array.prototype.slice.call(listingElements);	
}

function activeFilters() :string[] {
	return filterElementsArray().filter(element => element.checked).map(element => element.getAttribute('data-filter'));
}

function applyFilters() {
	var filters = activeFilters();
	listingElementsArray().forEach(element => {
		var elementClasses = element.className.split(' ');
		var matchingClasses = elementClasses.filter(elementClass => filters.indexOf(elementClass) != -1);
		var matchesFilters = matchingClasses.length == filters.length;
		element.style.display = (matchesFilters ? 'list-item' : 'none');
	});
	
	var filtersParam = filters.join(',');
	if (filtersParam) {
		window.history.replaceState(null, "", window.location.pathname + "?filter=" + filtersParam);
	} else {
		window.history.replaceState(null, "", window.location.pathname);
	}
}

function setFiltersFromParam(filtersParam: string) {
	var filters = filtersParam.split(',');
	var filterElements = filterElementsArray();
	filterElements.forEach(element => {
		var elementFilter = element.getAttribute('data-filter');
		element.checked = (filters.indexOf(elementFilter) !== -1);  
	});
}

function getQueryString(field: string, url?: string) {
    var href = url ? url : window.location.href;
    var reg = new RegExp( '[?&]' + field + '=([^&#]*)', 'i' );
    var string = reg.exec(href);
    return string ? string[1] : null;
}

window.addEventListener('load', event => {
	setFiltersFromParam(getQueryString('filter'));
	applyFilters();
});