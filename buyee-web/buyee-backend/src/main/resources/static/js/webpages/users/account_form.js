function checkPasswordMatch(confirmPassword) {
	if (confirmPassword.value != $("#inputPassword").val()) {
		confirmPassword.setCustomValidity("Password doesn't match!");
	} else {
		confirmPassword.setCustomValidity("");
	}
};