<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Mis consultas - Consultas Médicas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
	<%@ include file="/WEB-INF/views/encabezado.jspf"%>
	<div class="container">
		<h2>Mis consultas asignadas</h2>
		<p class="text-muted">Médico: <c:out value="${sessionScope.usuario.medico.nombre}" />
			(<c:out value="${sessionScope.usuario.medico.especialidad}" />). Solo lectura.</p>

		<table class="table table-striped bg-white">
			<thead class="table-dark">
				<tr>
					<th>Id</th>
					<th>Paciente</th>
					<th>Motivo</th>
					<th>Consultorio</th>
					<th>Horario</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="con" items="${consultas}">
					<tr>
						<td>${con.id}</td>
						<td><c:out value="${con.nombrePaciente}" /></td>
						<td><c:out value="${con.motivo}" /></td>
						<td>${con.numeroConsultorio}</td>
						<td>${con.horaInicio} - ${con.horaFin}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty consultas}">
					<tr><td colspan="5" class="text-center text-muted">No tiene consultas asignadas.</td></tr>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>
