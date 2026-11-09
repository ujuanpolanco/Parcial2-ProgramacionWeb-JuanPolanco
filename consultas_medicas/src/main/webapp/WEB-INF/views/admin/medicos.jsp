<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Médicos - Consultas Médicas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
	<%@ include file="/WEB-INF/views/encabezado.jspf"%>
	<div class="container">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2>Médicos</h2>
			<a class="btn btn-success" href="${pageContext.request.contextPath}/admin/medicos/nuevo">+ Nuevo médico</a>
		</div>

		<c:choose>
			<c:when test="${param.msg == 'creado'}"><div class="alert alert-success">Médico creado correctamente.</div></c:when>
			<c:when test="${param.msg == 'actualizado'}"><div class="alert alert-success">Médico actualizado correctamente.</div></c:when>
			<c:when test="${param.msg == 'eliminado'}"><div class="alert alert-success">Médico eliminado correctamente.</div></c:when>
			<c:when test="${param.msg == 'noexiste'}"><div class="alert alert-warning">El médico no existe.</div></c:when>
			<c:when test="${param.msg == 'tieneconsultas'}"><div class="alert alert-danger">No se puede eliminar: el médico tiene consultas asignadas.</div></c:when>
		</c:choose>

		<table class="table table-striped table-hover bg-white">
			<thead class="table-dark">
				<tr>
					<th>Id</th>
					<th>Nombre</th>
					<th>Especialidad</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="m" items="${medicos}">
					<tr>
						<td>${m.id}</td>
						<td><c:out value="${m.nombre}" /></td>
						<td><c:out value="${m.especialidad}" /></td>
						<td>
							<a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/admin/medicos/editar/${m.id}">Editar</a>
							<form action="${pageContext.request.contextPath}/admin/medicos/eliminar/${m.id}" method="post" class="d-inline"
								onsubmit="return confirm('¿Eliminar este médico?');">
								<button class="btn btn-sm btn-danger" type="submit">Eliminar</button>
							</form>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty medicos}">
					<tr><td colspan="4" class="text-center text-muted">No hay médicos registrados.</td></tr>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>
