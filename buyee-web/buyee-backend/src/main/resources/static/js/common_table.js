if (typeof jQuery === 'undefined') throw "jQuery Required";

// Delete button
$(document).ready(function() {
	$(".button-delete").on("click", function(e) {
		e.preventDefault();

		link = $(this);
		instanceId = link.attr("instanceId");
		instanceName = link.attr("instanceName");

		$("#confirmButton").attr("href", link.attr("href"));
		$("#modalBody").text("Do you confirm to delete: " + instanceName + " (ID: " + instanceId + ")?");
		$("#confirmModal").modal("show");
	});
});

// Hierarchical table
jQuery(function($) {
	var treeTable = {
		parentClassPrefix: '',
		collapsedClass: 'collapsed',
		init: function(parentClassPrefix) {
			this.parentClassPrefix = parentClassPrefix;
			$('table').on('click', 'tr', function() {
				treeTable.toggleRowChildren($(this));
			});
		},
		toggleRowChildren: function(parentRow) {
			var childClass = this.parentClassPrefix + parentRow.attr('id');
			var childrenRows = $('tr', parentRow.parent()).filter('.' + childClass);
			childrenRows.toggle();
			childrenRows.each(function() {
				if (!$(this).hasClass(treeTable.collapsedClass)) {
					treeTable.toggleRowChildren($(this));
				}
			});
			parentRow.toggleClass(this.collapsedClass);
		}
	};

	treeTable.init('parent_');
});
