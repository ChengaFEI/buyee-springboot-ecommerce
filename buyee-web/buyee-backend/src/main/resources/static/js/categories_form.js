// Check Email
function checkNameAlias(form) {
	nameAliasExist = checkNameAliasExist();
	if (nameAliasExist) checkNameAliasUnique(form);
	return false;
}

// Check Email Exists
function checkNameAliasExist() {
	instanceName = $("#inputName").val();
	instanceAlias = $("#inputAlias").val();
	if (instanceName == null || instanceName.length == 0) {
		showWarningModal("Email is required!");
		return false;
	}
	if (instanceAlias == null || instanceAlias.length == 0) {
		showWarningModal("Alias is required!");
		return false;
	}
	return true;
}

// Check Name and Alias Unique
function checkNameAliasUnique(form) {
	url = moduleURL + "/check_namealias";
	categoryId = $("#id").val();
	categoryName = $("#inputName").val();
	categoryAlias = $("#inputAlias").val();
	csrfValue = $("input[name='_csrf']").val();
	params = { id: categoryId, name: categoryName, alias: categoryAlias, _csrf: csrfValue };
	$.post(url, params, function(response) {
		if (response == "OK") form.submit();
		else if (response == "DuplicateName")
			showWarningModal("\"" + categoryName + "\" is used by others! Please enter another name!");
		else if (response == "DuplicateAlias")
			showWarningModal("\"" + categoryAlias + "\" is used by others! Please enter another alias!");
		else showErrorModal("Unknown response from the server!");
	}).fail(function() {
		showErrorModal("Could not connect to the server!");
	});
	return false;
}