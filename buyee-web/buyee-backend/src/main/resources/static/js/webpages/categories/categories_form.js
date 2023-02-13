// Check Email and Alias
function checkNameAlias(form) {
	let nameAliasExist = checkNameAliasExist();
	if (nameAliasExist) checkNameAliasUnique(form);
	return false;
}
// Step 1: Check Email and Alias Existence
function checkNameAliasExist() {
	let instanceName = $("#inputName").val();
	let instanceAlias = $("#inputAlias").val();
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
// Step 2: Check Name and Alias Unique
function checkNameAliasUnique(form) {
	let url = moduleURL + "/check_namealias";
	let categoryId = $("#id").val();
	let categoryName = $("#inputName").val();
	let categoryAlias = $("#inputAlias").val();
	let csrfValue = $("input[name='_csrf']").val();
	let params = { id: categoryId, name: categoryName, alias: categoryAlias, _csrf: csrfValue };
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