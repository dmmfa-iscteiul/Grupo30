<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="https://upload.wikimedia.org/wikipedia/commons/8/82/SARS-CoV-2_without_background.png">
<title>COVID QUERY</title>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">

<style>
.footer {
	position: absolute;
	bottom: 0;
	width: 100%;
	height: 60px;
	background-color: #f5f5f5;
}
</style>
</head>

<body>
	<nav class="navbar navbar-default">
		<a href="/" class="navbar-brand">COVID QUERY</a>

		<ul class="nav navbar-nav">
			<li class="active"><a href="#">Home</a></li>
		</ul>

		<ul class="nav navbar-nav navbar-right">
			<li><a href="/queries.do">2020</a></li>
		</ul>
	</nav>

	<div class="container">
		<H1>
			<b>Welcome To COVID QUERY</b>
		</H1>
		<caption>Latest statistical data in Portugal to monitor the
			social and economic impact of the Pandemic.</caption>
		<form action="/queries.do" method="post">
			<fieldset>
				<legend> </legend>
				<p>What are the regions?</p>
				<button type="submit" name="button" value="button1">Search</button>
			</fieldset>
			<br /> <br />
			<fieldset>
				<legend> </legend>
				<p>What is the total number of:</p>
				<select id="type" name="type">
					<option value="Testes">tests</option>
					<option value="Infecoes">infections</option>
					<option value="Internamentos">hospitalizations</option>
				</select>
				<p>In the region of:</p>
				<select id="region" name="region">
					<option value="Algarve">Algarve</option>
					<option value="Centro">Centro</option>
					<option value="Norte">Norte</option>
					<option value="Lisboa">Lisboa</option>
					<option value="Alentejo">Alentejo</option>
				</select>
				<p></p>
				<button type="submit" name="button" value="button2">Search</button>
			</fieldset>
			<br /> <br />
			<fieldset>
				<legend> </legend>
				<p>What is the total number of:</p>
				<select id="type1" name="type1">
					<option value="Testes">tests</option>
					<option value="Infecoes">infections</option>
					<option value="Internamentos">hospitalizations</option>
				</select> <select id="logicOperator" name="logicOperator">
					<option value="OR">OR</option>
				</select> <select id="type2" name="type2">
					<option value="Testes">tests</option>
					<option value="Infecoes">infections</option>
					<option value="Internamentos">hospitalizations</option>
				</select>
				<p></p>
				<button type="submit" name="button" value="button3">Search</button>
			</fieldset>
			<br /> <br />
			<fieldset>
				<legend> </legend>
				<p>Which regions where:</p>
				<select id="type0" name="type0">
					<option value="Testes">tests</option>
					<option value="Infecoes">infections</option>
					<option value="Internamentos">hospitalizations</option>
				</select> <select id="op" name="op">
					<option value=">">greater than</option>
					<option value="<">less than</option>
					<option value=">=">greater than or equal to</option>
					<option value="<=">less than or equal to</option>
				</select> <input type="text" id="number" name="number"><br> <br>
				<button type="submit" name="button" value="button4">Search</button>
			</fieldset>
		</form>
	</div>
</body>
</html>