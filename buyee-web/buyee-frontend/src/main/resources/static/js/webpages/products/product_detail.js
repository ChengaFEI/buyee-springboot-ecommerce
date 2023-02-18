// Show thumbnails
$(document).ready(function() {
	let bigImage = $("#image-big");
	$(".image-thumbnail").mouseover(function() {
		let currentImageSrc = $(this).attr("src");
		bigImage.attr("src", currentImageSrc);
	});
	$(".image-thumbnail").mouseout(function() {
		bigImage.attr("src", mainImagePathURL);
	});
});
