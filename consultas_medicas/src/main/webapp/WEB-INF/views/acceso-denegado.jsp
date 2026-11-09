<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Acceso denegado - Consultas Médicas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
	<%@ include file="/WEB-INF/views/encabezado.jspf"%>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-7">
				<div class="card border-danger shadow-sm">
					<div class="card-body text-center p-5">
						<h1 class="display-4 text-danger">403</h1>
						<h3 class="mb-3">Acceso denegado</h3>
						<p class="lead">No tiene permisos para realizar esta acción.</p>
						<c:if test="${not empty mensaje}">
							<div class="alert alert-warning"><c:out value="${mensaje}" /></div>
						</c:if>
						<p class="text-muted">
							Usuario actual: <strong><c:out value="${sessionScope.usuario.nombre}" /></strong>
							(rol <c:out value="${sessionScope.usuario.rol}" />)
						</p>
						<a class="btn btn-primary" href="${pageContext.request.contextPath}/">Volver a mi inicio</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
