<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Consultorios - Consultas Médicas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
	<%@ include file="/WEB-INF/views/encabezado.jspf"%>
	<div class="container">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2>Consultorios asignados</h2>
			<a class="btn btn-success" href="${pageContext.request.contextPath}/admin/consultas/nueva">+ Asignar consultorio</a>
		</div>

		<c:if test="${param.msg == 'asignado'}">
			<div class="alert alert-success">Consultorio asignado correctamente.</div>
		</c:if>

		<table class="table table-striped table-hover bg-white">
			<thead class="table-dark">
				<tr>
					<th>Id</th>
					<th>Paciente</th>
					<th>Motivo</th>
					<th>Consultorio</th>
					<th>Horario</th>
					<th>Médico</th>
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
						<td><c:out value="${con.medico.nombre}" /></td>
					</tr>
				</c:forEach>
				<c:if test="${empty consultas}">
					<tr><td colspan="6" class="text-center text-muted">No hay consultas registradas.</td></tr>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>
