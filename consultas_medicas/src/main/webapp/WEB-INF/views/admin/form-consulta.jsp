<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Asignar consultorio - Consultas Médicas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
	<%@ include file="/WEB-INF/views/encabezado.jspf"%>
	<div class="container" style="max-width: 650px;">
		<h2 class="mb-3">Asignar consultorio</h2>

		<c:if test="${not empty error}">
			<div class="alert alert-danger"><c:out value="${error}" /></div>
		</c:if>

		<form action="${pageContext.request.contextPath}/admin/consultas/guardar" method="post" class="card card-body">
			<div class="mb-3">
				<label class="form-label" for="nombrePaciente">Nombre del paciente</label>
				<input class="form-control" type="text" id="nombrePaciente" name="nombrePaciente" list="listaPacientes"
					maxlength="40" pattern="[A-Za-zÁÉÍÓÚáéíóúÑñÜü0-9 ]+" title="Solo letras, números y espacios (máximo 40)" required
					value="<c:out value='${form.nombrePaciente}'/>">
				<datalist id="listaPacientes">
					<c:forEach var="p" items="${pacientes}">
						<option value="<c:out value='${p.nombre}'/>"></option>
					</c:forEach>
				</datalist>
				<div class="form-text">Alfanumérico, máximo 40 caracteres.</div>
			</div>
			<div class="mb-3">
				<label class="form-label" for="motivo">Motivo de la consulta</label>
				<textarea class="form-control" id="motivo" name="motivo" maxlength="100" rows="2" required><c:out value="${form.motivo}" /></textarea>
				<div class="form-text">Máximo 100 caracteres.</div>
			</div>
			<div class="mb-3">
				<label class="form-label" for="numeroConsultorio">Número de consultorio</label>
				<input class="form-control" type="number" id="numeroConsultorio" name="numeroConsultorio" min="1" required
					value="${form.numeroConsultorio}">
			</div>
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
			<div class="mb-3">
				<label class="form-label" for="medicoId">Médico</label>
				<select class="form-select" id="medicoId" name="medicoId" required>
					<option value="">-- Seleccione un médico --</option>
					<c:forEach var="m" items="${medicos}">
						<option value="${m.id}" ${m.id == form.medicoId ? 'selected' : ''}>
							<c:out value="${m.nombre}" /> - <c:out value="${m.especialidad}" />
						</option>
					</c:forEach>
				</select>
			</div>
			<div>
				<button class="btn btn-primary" type="submit">Asignar</button>
				<a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/consultas">Cancelar</a>
			</div>
		</form>
	</div>
</body>
</html>
