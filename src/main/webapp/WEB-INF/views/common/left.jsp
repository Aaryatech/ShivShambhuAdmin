<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style>
html {
	overflow: scroll;
	overflow-x: hidden;
}

::-webkit-scrollbar {
	width: 0px; /* remove scrollbar space */
	background: transparent; /* optional: just make scrollbar invisible */
}
/* optional: show position indicator in red */
::-webkit-scrollbar-thumb {
	background: #FF0000;
}
</style>
</head>
<body>
	<aside id="left-panel" class="left-panel">
		<nav class="navbar navbar-expand-sm navbar-default">

			<div class="navbar-header">
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#main-menu" aria-controls="main-menu"
					aria-expanded="false" aria-label="Toggle navigation">
					<i class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand" href="./getLogin"> ShivShambhu Admin </a> <a
					class="navbar-brand hidden" href="./"> </a>
			</div>

			<div id="main-menu" class="main-menu collapse navbar-collapse">
				<ul class="nav navbar-nav"
					style="max-height: calc(100vh - 9rem); overflow-y: auto;">
					<li><a href="${pageContext.request.contextPath}/home"> <i
							class="menu-icon fa fa-dashboard"></i> Dashboard
					</a></li>

					<!--                     <h3 class="menu-title">UI elements</h3>/.menu-title
 -->

					<c:forEach items="${sessionScope.newModuleList}"
						var="allModuleList" varStatus="count">

						<c:choose>
							<c:when
								test="${allModuleList.moduleId==sessionScope.sessionModuleId}">
								<li class="active">
							</c:when>

							<c:otherwise>
								<li>
							</c:otherwise>
						</c:choose>
						<li class="menu-item-has-children dropdown"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="true"> <i
								class="menu-icon fa fa-table"></i> <c:out
									value="${allModuleList.moduleName}" />
						</a>
							<ul class="sub-menu children dropdown-menu ">
								<c:forEach items="${allModuleList.subModuleJsonList}"
									var="allSubModuleList">

									<li class="active"><i class="fa fa-puzzle-piece"></i> <c:choose>
											<c:when
												test="${allSubModuleList.subModuleId==sessionScope.sessionSubModuleId}">
												<li class="active">
											</c:when>
											<c:otherwise>
												<li>
											</c:otherwise>
										</c:choose> <a
										onclick="selectSubMod(${allSubModuleList.subModuleId},${allSubModuleList.moduleId})"
										href="${pageContext.request.contextPath}/<c:out value="${allSubModuleList.subModuleMapping}" />"><c:out
												value="${allSubModuleList.subModulName}" /></a></li>

								</c:forEach>

							</ul></li>
					</c:forEach>




				</ul>

			</div>

		</nav>
	</aside>
	<!-- /#left-panel -->

	<!-- Left Panel -->

</body>
</html>