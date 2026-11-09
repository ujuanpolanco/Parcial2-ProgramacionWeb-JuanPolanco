<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Mis citas - Consultas Médicas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
	<%@ include file="/WEB-INF/views/encabezado.jspf"%>
	<div class="container">
		<h2>Mis citas</h2>
		<p class="text-muted">Paciente: <c:out value="${sessionScope.usuario.nombre}" /></p>

		<c:choose>
			<c:when test="${param.msg == 'actualizado'}"><div class="alert alert-success">Horario actualizado correctamente.</div></c:when>
			<c:when test="${param.msg == 'noexiste'}"><div class="alert alert-warning">La cita no existe.</div></c:when>
		</c:choose>

		<table class="table table-striped bg-white">
			<thead class="table-dark">
				<tr>
					<th>Id</th>
					<th>Motivo</th>
					<th>Consultorio</th>
					<th>Horario</th>
					<th>Médico</th>
					<th>Acción</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="con" items="${citas}">
					<tr>
						<td>${con.id}</td>
						<td><c:out value="${con.motivo}" /></td>
						<td>${con.numeroConsultorio}</td>
						<td>${con.horaInicio} - ${con.horaFin}</td>
						<td><c:out value="${con.medico.nombre}" /></td>
						<td><a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/paciente/citas/horario/${con.id}">Cambiar horario</a></td>
					</tr>
				</c:forEach>
				<c:if test="${empty citas}">
					<tr><td colspan="6" class="text-center text-muted">No tiene citas asignadas.</td></tr>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>
