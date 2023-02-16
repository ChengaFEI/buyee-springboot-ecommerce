$(document).ready(function() {
	// Display the image thumbnail
	$("input[name='extraImageFile']").each(function(index) {
		$(this).change(function() {
			if (checkFileSize(this)) {
				showExtraImageThumbnail(this, index);
			}
		});
	});
	// Remove the stored extra images section
	$("a[name='linkRemoveExtraImage']").each(function(index) {
		$(this).click(function() {
			removeExtraImageSection(index);
		});
	});
});

// Display extra images
function showExtraImageThumbnail(fileInput, index) {
	// Display image
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#extraThumbnail" + index).attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
	// Replace the hidden image name of the extra image section
	fileName = file.name;
	extraImageNameHiddenField = $("#extraImageName" + index);
	if (extraImageNameHiddenField != null && extraImageNameHiddenField.length) {
		extraImageNameHiddenField.val(fileName); 
	}
	// Add extra image section
	showNextExtraImageSection(index+1);
}

// Add extra image section
function showNextExtraImageSection(index) {
	html = `
		<div id="extraImageDiv${index}" class="form-group mb-3 rounded" style="width: 25%" 
			 title="Image should be smaller than 1MB">
			<label class="col-form-label">Extra Image ${index+1}</label>
			<div id="extraImageHeader${index}" class="input-group mb-3" >
				<input type="hidden" id="isImageExist${index}" value="false">
				<input class="form-inline form-control text-muted cf-file" type="file" name="extraImageFile" 
					   accept="image/png image/jpeg" onchange="showExtraImageThumbnail(this, ${index})">
			</div>
			<img id="extraThumbnail${index}" alt="Extra Image ${index+1} Preview" style="width: 100%" 
				 src="${defaultImageThumbnail}"/>
		</div>
	`;
	htmlLinkRemove = `
		<a class="input-group-text border-0 cf-button-form-delete-image" title="Delete this image"
		   href="javascript:removeExtraImageSection(${index-1})"> 
		    <i class="fa-solid fa-xmark" style="height: 100%; padding: 0.3rem"></i>
		</a>
	`;
	isImageExist = document.getElementById("isImageExist" + (index-1));
	if (isImageExist.value == "false") {
		$("#divProductImages").append(html);
		$("#extraImageHeader" + (index-1)).append(htmlLinkRemove);
		isImageExist.value = "true"
	}
}

// Delete extra image sections
function removeExtraImageSection(index) {
	$("#extraImageDiv" + index).remove();
}
