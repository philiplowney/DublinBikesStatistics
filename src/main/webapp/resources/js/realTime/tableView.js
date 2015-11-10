function updateClasses()
{
	$(".full").parent("td").addClass("full");
	$(".empty").parent("td").addClass("empty");
}

$(document).ready(function()
{
	updateClasses();
});
