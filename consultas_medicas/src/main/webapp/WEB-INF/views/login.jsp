<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Iniciar sesión - Consultas Médicas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/estilos.css" rel="stylesheet">
</head>
<body class="bg-light">
	<div class="container">
		<div class="row justify-content-center mt-5">
			<div class="col-md-5">
				<div class="card shadow-sm">
					<div class="card-body p-4">
						<h3 class="text-center mb-1">Consultas Médicas</h3>
						<p class="text-center text-muted mb-4">Ingrese sus credenciales</p>

						<c:if test="${not empty error}">
							<div class="alert alert-danger"><c:out value="${error}" /></div>
						</c:if>
						<c:if test="${param.salio == 'true'}">
							<div class="alert alert-success">Sesión cerrada correctamente.</div>
						</c:if>

						<form action="${pageContext.request.contextPath}/login" method="post">
							<div class="mb-3">
								<label class="form-label" for="username">Usuario</label>
								<input class="form-control" type="text" id="username" name="username"
									value="<c:out value='${username}'/>" maxlength="30" required autofocus>
							</div>
							<div class="mb-3">
								<label class="form-label" for="password">Contraseña</label>
								<input class="form-control" type="password" id="password" name="password" maxlength="60" required>
							</div>
							<button class="btn btn-primary w-100" type="submit">Iniciar sesión</button>
						</form>
					</div>
				</div>

				<div class="card mt-3">
					<div class="card-body small">
						<strong>Usuarios de prueba</strong> (contraseña <code>1234</code>):
						<ul class="mb-0">
							<li>Administrador: <code>admin</code></li>
							<li>Médicos: <code>jpolanco</code>, <code>sramos</code>, <code>amayor</code></li>
							<li>Pacientes: <code>pablo</code>, <code>samuel</code></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
