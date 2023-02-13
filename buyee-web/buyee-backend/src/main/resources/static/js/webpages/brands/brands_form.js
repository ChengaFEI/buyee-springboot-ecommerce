// Display selected categories
$(document).ready(function() {
	dropdownCategories = $("#categories");
	selectedCategories = $("#selectedCategories");
	showSelectedCategories(dropdownCategories, selectedCategories);
	dropdownCategories.change(function() {
		selectedCategories.empty();
		showSelectedCategories(dropdownCategories, selectedCategories)
	});
});
