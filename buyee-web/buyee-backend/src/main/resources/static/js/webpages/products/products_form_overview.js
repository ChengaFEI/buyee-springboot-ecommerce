// Brand and categories blanks
dropdownBrands = $("#brand");
dropdownCategories = $("#category");
// Format the description blank
$(document).ready(function() {
	dropdownBrands.change(function() {
		dropdownCategories.empty();
		readCategories();
	});
	readCategoriesForNewForm();
});
// Display categories of the selected brand 
function readCategories() {
	let brandId = dropdownBrands.val();
	let url = brandModuleURL + "/" + brandId + "/categories";
	$.get(url, function(responseJson) {
		$.each(responseJson, function(index, category) {
			$("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
		});
	});
}
// Display pre-selected category of the product
function readCategoriesForNewForm() {
	let categoryIdField = $("#categoryId");
	let editMode = (categoryIdField.length != 0);
	if (!editMode) readCategories();
}
