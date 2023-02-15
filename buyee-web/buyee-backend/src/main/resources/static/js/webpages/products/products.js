$(document).ready(function() {
	// Prepare to show preview modal
	$(".link-detail").on("click", function(e) {
		e.preventDefault();
		let linkDetailURL = $(this).attr("href");
		$("#previewModal").modal("show").find(".modal-content").load(linkDetailURL);
	});
	// Prepare to show filtered products
	filterProductByCategory("#filterLargeScreen");
	filterProductByCategory("#filterSmallScreen");
});
// Filter products by category
function filterProductByCategory(filterFormId) {
	var filterForm = $(filterFormId);
	filterForm.find(".filter-select").on("change", function() {
		filterForm.submit();
	});
}
