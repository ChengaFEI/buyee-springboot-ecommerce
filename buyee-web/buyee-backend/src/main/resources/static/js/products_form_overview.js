// Brand and categories blanks
dropdownBrands = $("#brand");
dropdownCategories = $("#category");

// Format the description blank
$(document).ready(function() {
	dropdownBrands.change(function() {
		dropdownCategories.empty();
		readCategories();
	});
	readCategories();
});

// Display categories of the selected brand 
function readCategories() {
	brandId = dropdownBrands.val();
	url = brandModuleURL + "/" + brandId + "/categories";
	$.get(url, function(responseJson) {
		$.each(responseJson, function(index, category) {
			$("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
		});
	});
}
