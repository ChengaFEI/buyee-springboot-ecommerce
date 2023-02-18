window.addEventListener("scroll", function() {
	var box = document.getElementById("scrollBox");
	var boxTop = box.getBoundingClientRect().top;
	var scrollPos = window.scrollY || window.scrollTop || document.getElementsByTagName("html")[0].scrollTop;
	if (scrollPos > boxTop) {
		box.style.boxShadow = "0 4px 8px rgba(0, 0, 0, 0.3)";
	} else {
		box.style.boxShadow = "0 0 0 rgba(0, 0, 0, 0.3)";
	}
});
