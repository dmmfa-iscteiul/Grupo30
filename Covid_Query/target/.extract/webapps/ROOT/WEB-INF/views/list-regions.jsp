<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div class="container">
	<H1><b>Query:</b> ${query}</H1>
	
	<table class="table table-striped">
		<thead>
			<tr><th>Description</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${todos}" var="todo">
				<tr>
					<td>${todo.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a class="btn btn-success" href="/queries.do">Back</a>
</div>