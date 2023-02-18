// Show thumbnails
$(document).ready(function() {
	let bigImage = $("#image-big");
	$(".image-thumbnail").mouseover(function() {
		let currentImageSrc = $(this).attr("src");
		let currentImageIndex = $(this).attr("data-index");
		bigImage.attr("src", currentImageSrc);
		bigImage.attr("data-index", currentImageIndex);
	});
	bigImage.mouseover(function() {
		bigImage.attr("data-index", 0);
	});
	$(".image-thumbnail").mouseout(function() {
		bigImage.attr("src", mainImagePathURL);
	});
	$(".image-thumbnail").on("click", function() {
		$("#carouselModal").modal("show");
		imageIndex = parseInt(bigImage.attr("data-index"));
		$("#carouselIndicators").carousel(imageIndex);
	});
});
