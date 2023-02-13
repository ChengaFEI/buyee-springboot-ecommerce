// Check Email
function checkEmail(form) {
	let emailExist = checkEmailExist();
	if (emailExist) checkEmailUnique(form);
	return false;
}
// Check Email Exists
function checkEmailExist() {
	let instanceEmail = $("#inputEmail").val();
	if (instanceEmail != null && instanceEmail.length != 0) return true;
	else {
		showWarningModal("Email is required!");
		return false;
	}
}
// Check Email Unique
function checkEmailUnique(form) {
	let url = moduleURL + "/check_email";
	let userId = $("#id").val();
	let userEmail = $("#inputEmail").val();
	let csrfValue = $("input[name='_csrf']").val();
	let params = { id: userId, email: userEmail, _csrf: csrfValue };
	$.post(url, params, function(response) {
		if (response == "OK") form.submit();
		else if (response == "Duplicate") {
			showWarningModal("\"" + userEmail + "\" is used by others! Please enter another email!");
		} else showErrorModal("Unknown response from the server!");
	}).fail(function() {
		showErrorModal("Could not connect to the server!");
	});
	return false;
}
