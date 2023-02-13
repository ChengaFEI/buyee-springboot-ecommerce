// Display the thumbnail of uploaded image
$(document).ready(function() {
	$("#cancelButton").on("click", function() {
		window.location = moduleURL;
	});

	$("#imageFile").change(function() {
		if (checkFileSize(this)) {
			showImageThumbnail(this);
		}
	});
});

// Check File Size
function checkFileSize(fileInput) {
	fileSize = fileInput.files[0].size;
	if (fileSize > MAX_FILE_SIZE) {
		fileInput.setCustomValidity("Image is larger than " + MAX_FILE_SIZE + " bytes!");
		fileInput.reportValidity();
		return false;
	} else {
		fileInput.setCustomValidity("");
		return true;
	}
}

// Image Preview
function showImageThumbnail(fileInput) {
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#thumbnail").attr("src", e.target.result);
	};
	reader.readAsDataURL(file);
};

// Modal Dialog
function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal("show");
}

function showWarningModal(message) {
	showModalDialog("Warning", message);
}

function showErrorModal(message) {
	showModalDialog("Error", message);
}

// Multi-Selection Blanks Preview
function showSelectedCategories(dropdownCategories, selectedCategories) {
	dropdownCategories.children("option:selected").each(function() {
		selectedCategory = $(this);
		catId = selectedCategory.val();
		catName = selectedCategory.text().replace(/·/g, "");
		selectedCategories.append(
			"<span class='badge rounded-pill text-bg-light fw-normal badge-custom' style='margin-right:10px'>"
			+ catName +
			"</span>");
	});
}

// Check Name
function checkName(form) {
	nameExist = checkNameExist();
	if (nameExist) nameUnique = checkNameUnique(form);
	return false;
}

// Check Name Exists
function checkNameExist() {
	instanceName = $("#inputName").val();
	if (instanceName != null && instanceName.length != 0) return true;
	else {
		showWarningModal("Name is required!");
		return false;
	}
}

// Check Name Uniqueness
function checkNameUnique(form) {
	url = moduleURL + "/check_name";
	instanceId = $("#id").val();
	instanceName = $("#inputName").val();
	csrfValue = $("input[name = '_csrf']").val();
	params = { id: instanceId, name: instanceName, _csrf: csrfValue };
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
