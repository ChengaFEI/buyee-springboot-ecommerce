// Display the thumbnail of uploaded image
$(document).ready(function() {
	$(".cancelButton").on("click", function() {
		window.location = moduleURL;
	});

	$("#imageFile").change(function() {
		if (checkFileSize(this)) {
			showImageThumbnail(this);
		}
	});
});
// Step 1: Check File Size
function checkFileSize(fileInput) {
	let fileSize = fileInput.files[0].size;
	if (fileSize > MAX_FILE_SIZE) {
		fileInput.setCustomValidity("Image is larger than " + MAX_FILE_SIZE + " bytes!");
		fileInput.reportValidity();
		return false;
	} else {
		fileInput.setCustomValidity("");
		return true;
	}
}
// Step 2: Image Preview
function showImageThumbnail(fileInput) {
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
};
// Show Modal Dialog
function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal("show");
}
// Type 1: Warning
function showWarningModal(message) {
	showModalDialog("Warning", message);
}
// Type 2: Error
function showErrorModal(message) {
	showModalDialog("Error", message);
}
// Presubmission Name Check
function checkName(form) {
	let nameExist = checkNameExist();
	if (nameExist) checkNameUnique(form);
	return false;
}
// Step 1: Check existence
function checkNameExist() {
	let instanceName = $("#inputName").val();
	if (instanceName != null && instanceName.length != 0) return true;
	else {
		showWarningModal("Name is required!");
		return false;
	}
}
// Step 2: Check uniqueness
function checkNameUnique(form) {
	let url = moduleURL + "/check_name";
	let instanceId = $("#id").val();
	let instanceName = $("#inputName").val();
	let csrfValue = $("input[name = '_csrf']").val();
	let params = { id: instanceId, name: instanceName, _csrf: csrfValue };
	$.post(url, params, function(response) {
		if (response == "OK") form.submit();
		else if (response == "Duplicate") {
			showWarningModal("\"" + instanceName + "\" is used by others! Please enter another name!");
		} else showErrorModal("Unknown response from the server!");
	}).fail(function() {
		showErrorModal("Could not connect to the server!");
	});
	return false;
}
