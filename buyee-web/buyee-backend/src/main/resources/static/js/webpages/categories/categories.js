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
