$(document).ready(function() {
	$(".link-detail").on("click", function(e) {
		e.preventDefault();
		let linkDetailURL = $(this).attr("href");
		$("#previewModal").modal("show").find(".modal-content").load(linkDetailURL);
	});
});
