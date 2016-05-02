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
		var elementClasses = element.className.split(" ");
		var matchingClasses = elementClasses.filter(elementClass => filters.indexOf(elementClass) != -1);
		var matchesFilters = matchingClasses.length == filters.length;
		element.style.display = (matchesFilters ? "list-item" : "none");
	});
}
