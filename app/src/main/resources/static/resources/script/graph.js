/*<![CDATA[*/
$(document).ready(function () {

	console.log($.get)
/*	test change  asdf*/
	var chart = c3.generate({
	    data: {
	        x: 'x',
	        columns: [
	            ['x', 30, 50, 100, 230, 300, 310],
	            ['Verkaeufe', 30, 200, 100, 400, 150, 250],
	        ]
	    }
	});


	setTimeout(function () {
	    chart.load({
	        columns: [
	            ['Rueckgaben', 100, 250, 150, 200, 100, 350]
	        ]
	    });
	}, 200);



	setTimeout(function () {
	    chart.load({
	        columns: [
	            ['Einkaeufe', 10, 150, 100, 180, 80, 150]
	        ]
	    });
	}, 400);

	setTimeout(function () {
	    chart.load({
	        columns: [
	            ['Umsatz', 20, 150, 100, 180, 80, 150]
	        ]
	    });
	}, 600);

	setTimeout(function () {
	    chart.load({
	        columns: [
	            ['Gewinn', 40, 150, 100, 180, 80, 150]
	        ]
	    });
	}, 800);


/*	setTimeout(function () {
	    chart.unload({
	        ids: 'data2'
	    });
	}, 2000);*/

});

/*]]>*/
