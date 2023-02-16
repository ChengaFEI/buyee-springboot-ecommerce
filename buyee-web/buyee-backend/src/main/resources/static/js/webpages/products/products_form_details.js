$(document).ready(function() {
	// Remove stored details by clicking button
	$(".linkRemoveDetail").each(function(index) {
		$(this).click(function() {
			removeDetailSectionByIndex(index);
		});
	});
});
// Add extra detail section
function showNextDetailSection() {
	allDivDetails = $("[id^='divDetail']");
	divDetailsNum = allDivDetails.length;
	nextDivDetailId = divDetailsNum
	// Add extra detail section
	html = `
		<div id="divDetail${nextDivDetailId}" class="row mb-3">
			<input type="hidden" name="detailIds" value="0"/>
			<div class="input-group cf-shrinkable-bar-long" style="width: calc(50% - 30px)" 
				 title="Name must be 2-255 characters long">
				<label class="detail-label-padding">Name:</label>
				<input type="text" class="form-control rounded" name="detailNames" pattern=".{2,255}"/>
			</div>
			<div class="input-group cf-shrinkable-bar-short" style="width: calc(50% - 30px)" 
				 title="Value must be 2-255 characters long">
				<label class="detail-label-padding">Value:</label> 
				<input type="text" class="form-control rounded" name="detailValues" pattern=".{2,255}"/>
			</div>
		</div>
	`;
	$("#divProductDetails").append(html);
	// Add remove button to the newly added detail section
	allDivDetails = $("[id^='divDetail']");
	lastDivDetail = allDivDetails.last();
	lastDivDetailId = lastDivDetail.attr("id");
	htmlLinkRemove = `
		<div class="row input-group" style="width: 65px; margin-left: auto;" title="Delete this detail">
			<a class="detail-button-padding cf-button-form-delete-detail" 
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

function removeDetailSectionByIndex(index) {
	$("#divDetail" + index).remove();
}
