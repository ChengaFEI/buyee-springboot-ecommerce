if (typeof jQuery === 'undefined') throw "jQuery Required";
// Delete button
$(document).ready(function() {
	$(".button-delete").on("click", function(e) {
		e.preventDefault();
		let link = $(this);
		let instanceId = link.attr("instanceId");
		let instanceName = link.attr("instanceName");
		$("#confirmButton").attr("href", link.attr("href"));
		$("#modalBody").text("Do you confirm to delete: " + instanceName + " (ID: " + instanceId + ")?");
		$("#confirmModal").modal("show");
	});
});
