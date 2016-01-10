<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Statistiken</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="/resources/css/main.css" rel="stylesheet" type="text/css">
<link href="/resources/css/admin.css" rel="stylesheet" type="text/css">
<script src="/resources/script/jquery.min.js"></script>
<script src="/resources/script/plotly-latest.min.js"></script>
<script type="text/javascript">
	
	var stats = new Array();
	<c:forEach items="${stats}" var="stat">
		var statistic = new Object();
		statistic.turnover = '${stat.turnover}';
		statistic.orders = '${stat.orders}';
		statistic.returns = '${stat.returns}';
		statistic.expenses = '${stat.expenses}';
		statistic.profit = '${stat.profit}';
		stats.push(statistic);
	</c:forEach>
	$( document ).ready(function() {
	    console.log( "ready!" );
	});
</script>
</head>

<body>
<div id="wrapper">
	<div id="header" th:include="navigation :: navigation"></div>

	<main>
		<div id="mainframe">
			<div id="topLine">
				<div id="path">
					<a href="/"><img id="home" src="/resources/Bilder/home.png" /></a>
					<img id="breadcrumb" src="/resources/Bilder/forward.png" />
					<a href="/admin" >Adminportal</a>
					<img id="breadcrumb" src="/resources/Bilder/forward.png" />
					<a href="/admin/statistics" >Statistiken</a>
				</div>
			</div>
			
			<div id="workframe">
				<div id="graph-wrapper">
					<div class="graph-info">
        				<a href="javascript:void(0)" id="sales" class="dataset">Verkäufe</a>
        				<a href="javascript:void(0)" id="refunds" class="dataset">Rückgaben</a>
        				<a href="javascript:void(0)" id="purchases" class="dataset">Einkäufe</a>
        				<a href="javascript:void(0)" id="turnover" class="dataset">Umsatz</a>
        				<a href="javascript:void(0)" id="profit" class="dataset">Gewinn</a>
        				
        				<a href="#" id="bars"><span></span></a>
						<a href="#" id="lines" class="active"><span></span></a>
    				</div>
					<div class="graph-container">
						<div id="graph-lines"></div>
						<div id="graph-bars"></div>
					</div>
				</div>
			</div>
		</div>
	</main>
	<div id="footer" th:include="footer :: footer"></div>
</div>
</body>
</html>
