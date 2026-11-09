<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Cambiar horario - Consultas Médicas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
	<%@ include file="/WEB-INF/views/encabezado.jspf"%>
	<div class="container" style="max-width: 600px;">
		<h2 class="mb-3">Cambiar horario de la cita</h2>

		<c:if test="${not empty error}">
			<div class="alert alert-danger"><c:out value="${error}" /></div>
		</c:if>

		<div class="card card-body mb-3">
			<p class="mb-1"><strong>Motivo:</strong> <c:out value="${cita.motivo}" /></p>
			<p class="mb-1"><strong>Consultorio:</strong> ${cita.numeroConsultorio}</p>
			<p class="mb-0"><strong>Médico:</strong> <c:out value="${cita.medico.nombre}" /></p>
		</div>

		<form action="${pageContext.request.contextPath}/paciente/citas/horario/${cita.id}" method="post" class="card card-body">
			<div class="row mb-3">
				<div class="col">
					<label class="form-label" for="horaInicio">Hora de inicio</label>
					<input class="form-control" type="time" id="horaInicio" name="horaInicio" required value="${form.horaInicio}">
				</div>
				<div class="col">
					<label class="form-label" for="horaFin">Hora de finalización</label>
					<input class="form-control" type="time" id="horaFin" name="horaFin" required value="${form.horaFin}">
				</div>
			</div>
			<div>
				<button class="btn btn-primary" type="submit">Guardar horario</button>
				<a class="btn btn-secondary" href="${pageContext.request.contextPath}/paciente/citas">Cancelar</a>
			</div>
		</form>
	</div>
</body>
</html>
