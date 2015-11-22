function loadAvailabilityChart(bikesAvailable, spacesAvailable)
{
	google.load("visualization", "1",
	{
		packages : [ "corechart" ]
	});
	google.setOnLoadCallback(drawChart);
	function drawChart()
	{
		var data = google.visualization.arrayToDataTable([ [ 'Resource', 'Quantity' ], [ 'Bikes', bikesAvailable ], [ 'Spaces', spacesAvailable ] ]);
		var options =
		{
			pieHole : 0.5,
			pieSliceText : 'label',
			legend : 'none',
			chartArea :
			{
				width : '90%',
				height : '90%'
			}
		};
		var chart = new google.visualization.PieChart(document.getElementById('piechart'));
		chart.draw(data, options);
	}
}
