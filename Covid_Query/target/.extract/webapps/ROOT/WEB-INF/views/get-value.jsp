<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div class="container">
	<H1><b>Query:</b> ${query}</H1>
	<table class="table table-striped">
		<thead>
			<tr><th>Description</th></tr>
		</thead>
		<tbody>
				<tr>
					<td>${val}</td>
				</tr>
		</tbody>
	</table>
	<a class="btn btn-success" href="/queries.do">Back</a>
</div>
