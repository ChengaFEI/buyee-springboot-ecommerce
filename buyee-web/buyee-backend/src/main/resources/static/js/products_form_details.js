// Add extra detail section
function showNextDetailSection() {
	allDivDetails = $("[id^='divDetail']");
	divDetailsNum = allDivDetails.length;
	nextDivDetailId = divDetailsNum
	
	// Add extra detail section
	html = `
		<div id="divDetail${nextDivDetailId}" class="row mb-3">
			<div class="input-group" style="width: 47.5%" title="Name must be 2-255 characters long">
				<label class="detail-label-padding">Name:</label>
				<input type="text" class="form-control rounded" name="detailName" pattern=".{2,255}"/>
			</div>
			<div class="input-group" style="width: 47.5%" title="Value must be 2-255 characters long">
				<label class="detail-label-padding">Value:</label>
				<input type="text" class="form-control rounded" name="detailValue" pattern=".{2,255}"/>
			</div>
		</div>
	`;
	$("#divProductDetails").append(html);
	
	// Add remove button to the newly added detail section
	allDivDetails = $("[id^='divDetail']");
	lastDivDetail = allDivDetails.last();
	lastDivDetailId = lastDivDetail.attr("id");
	htmlLinkRemove = `
		<div class="input-group" style="width: 5%" title="Delete this detail">
			<a class="detail-button-padding detail-button-manager" 
			   onclick="removeDetailSectionById('${lastDivDetailId}');">
			    <i class="fa-solid fa-square-xmark fa-2x" style="height: 100% ; padding: 0.1rem"></i>
			</a>	
		</div>
	`;
	lastDivDetail.append(htmlLinkRemove);
}

// Delete extra detail sections
function removeDetailSectionById(id) {
	$("#" + id).remove();
}