$(document).ready(function() {
	// Display selected categories
	let dropdownCategories = $("#categories");
	var selectedCategories = $("#selectedCategories");
	// Step 1: Display originally selected categories
	showSelectedCategories(dropdownCategories, selectedCategories);
	// Step 2: Display changed categories
	dropdownCategories.change(function() {
		// Step 2.1: Clear previous selections
		selectedCategories.empty();
		// Step 2.2: Show new selections
		showSelectedCategories(dropdownCategories, selectedCategories)
	});
});
function showSelectedCategories(dropdownCategories, selectedCategories) {
	dropdownCategories.children("option:selected").each(function() {
		let selectedCategory = $(this);
		let catName = selectedCategory.text().replace(/·/g, "");
		selectedCategories.append(
			"<span class='badge rounded-pill text-bg-light fw-normal cf-badge' style='margin-right:10px'>"
			+ catName +
			"</span>");
	});
}
