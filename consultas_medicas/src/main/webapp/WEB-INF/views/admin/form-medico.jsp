<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>${titulo} - Consultas Médicas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
	<%@ include file="/WEB-INF/views/encabezado.jspf"%>
	<div class="container" style="max-width: 600px;">
		<h2 class="mb-3">${titulo}</h2>

		<c:if test="${not empty error}">
			<div class="alert alert-danger"><c:out value="${error}" /></div>
		</c:if>

		<form action="${pageContext.request.contextPath}${accion}" method="post" class="card card-body">
			<div class="mb-3">
				<label class="form-label" for="nombre">Nombre</label>
				<input class="form-control" type="text" id="nombre" name="nombre" maxlength="60" required
					value="<c:out value='${form.nombre}'/>">
			</div>
			<div class="mb-3">
				<label class="form-label" for="especialidad">Especialidad</label>
				<input class="form-control" type="text" id="especialidad" name="especialidad" maxlength="60" required
					value="<c:out value='${form.especialidad}'/>">
			</div>
			<div class="mb-3">
				<label class="form-label" for="username">Usuario para iniciar sesión</label>
				<input class="form-control" type="text" id="username" name="username" maxlength="30" required
					value="<c:out value='${form.username}'/>">
			</div>
			<div class="mb-3">
				<label class="form-label" for="password">Contraseña</label>
				<c:choose>
					<c:when test="${esNuevo}">
						<input class="form-control" type="password" id="password" name="password" maxlength="60" required>
					</c:when>
					<c:otherwise>
						<input class="form-control" type="password" id="password" name="password" maxlength="60"
							placeholder="Déjela vacía para conservar la actual">
					</c:otherwise>
				</c:choose>
			</div>
			<div>
				<button class="btn btn-primary" type="submit">Guardar</button>
				<a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/medicos">Cancelar</a>
			</div>
		</form>
	</div>
</body>
</html>
