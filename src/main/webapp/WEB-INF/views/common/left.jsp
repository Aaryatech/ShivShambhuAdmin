<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ats.ssgs.common.Constants"%>

<html>
<head>
<style>
html {
	overflow: scroll;
	overflow-x: hidden;
}
a{
color:white;
}
.hightlight{
font-size:14;
}
::-webkit-scrollbar {
	width: 0px; /* remove scrollbar space */
	background: transparent; /* optional: just make scrollbar invisible */
}
/* optional: show position indicator in red */
::-webkit-scrollbar-thumb {
	background: #FF0000;
}
.form-control {
  display: block;
  width: 100%;
  padding: 0.05rem 0.10rem;
  }
  .btn-primary {
    color: #fff;
    background-color:#58bdc3 ;
    border-color: #58bdc3;
    border-radius:25px;
}
.btn-primary:hover {
  color: #fff;
  background-color: #58bdc3;
  border-color:  #58bdc3;
}
::-webkit-scrollbar {
  width: 7px;
  height: 7px;
}
::-webkit-scrollbar-button {
  width: 0px;
  height: 0px;
}
::-webkit-scrollbar-thumb {
  background: #636269;
  border: 0px none #ffffff;
  border-radius: 50px;
}
::-webkit-scrollbar-thumb:hover {
  background: #636269;
}
::-webkit-scrollbar-thumb:active {
  background: #636269;
}
::-webkit-scrollbar-track {
  background: #333238;
  border: 0px none #ffffff;
  border-radius: 50px;
}
::-webkit-scrollbar-track:hover {
  background: #333238;
}
::-webkit-scrollbar-track:active {
  background: #333238;
}
::-webkit-scrollbar-corner {
  background: transparent;
}
  
</style>
<link href="https://fonts.googleapis.com/css?family=Abril+Fatface" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Merriweather" rel="stylesheet">

</head>
<body>
	<c:url var="setSubModId" value="/setSubModId"></c:url>
	<aside id="left-panel" class="left-panel">
		<nav class="navbar navbar-expand-sm navbar-default">

			<div class="navbar-header" >
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#main-menu" aria-controls="main-menu"
					aria-expanded="false" aria-label="Toggle navigation">
					<i class="fa fa-bars"></i>
				</button>
				<a class="navbar-brand" href="./getLogin" style="font-family: 'Abril Fatface', cursive;"> ShivShambhu Admin </a> <a
					class="navbar-brand hidden" href="./"> </a>
			</div>

			<div id="main-menu" class="main-menu collapse navbar-collapse">
				<ul class="nav navbar-nav"
					>
					<%-- style="max-height: calc(100vh - 9rem); overflow-y: auto;"<li><a href="${pageContext.request.contextPath}/home"> <i
							class="menu-icon fa fa-dashboard"></i> Dashboard
					</a></li> --%>

					<!--                     <h3 class="menu-title">UI elements</h3>/.menu-title
 -->

					<c:forEach items="${sessionScope.newModuleList}"
						var="allModuleList" varStatus="count">
                       <c:set var="classa" value="menu-item-has-children dropdown "></c:set>
                           <c:set var="classb" value="sub-menu children dropdown-menu"></c:set>
                          <c:set var="flag" value="0"></c:set>
						<c:choose>
							<c:when
								test="${allModuleList.moduleId==sessionScope.sessionModuleId}">
								  <c:set var="classa" value="menu-item-has-children dropdown show"/>
								   <c:set var="classb" value="sub-menu children dropdown-menu show"/>
							</c:when>

							<c:otherwise>
								 <c:set var="classa" value="menu-item-has-children dropdown"/>
								   <c:set var="classb" value="sub-menu children dropdown-menu"/>
							</c:otherwise>
						</c:choose>
						<li class="${classa}"><a href="#" style="font-family: 'Merriweather', serif;"
							class="dropdown-toggle" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="true"> <i
								class="menu-icon fa fa-table"></i> <c:out
									value="${allModuleList.moduleName}" />
						</a>
							<ul class="${classb}">
								<c:forEach items="${allModuleList.subModuleJsonList}"
									var="allSubModuleList">
                             <c:set var="textcolor" value=""/>
									<li class="menu-item-has-children dropdown"><!-- <i class="fa fa-puzzle-piece"></i>  --><c:choose>
											<c:when
												test="${allSubModuleList.subModuleId==sessionScope.sessionSubModuleId}">
												<li class="menu-item-has-children dropdown show">
												<c:set var="textcolor" value="green"/>
												
											</c:when>
											<c:otherwise>
												<li>
												<c:set var="textcolor" value=""/>
											</c:otherwise>
										</c:choose> <a  style="font-family:  'Merriweather', serif;"
										onclick="selectSubMod(${allSubModuleList.subModuleId},${allSubModuleList.moduleId})"
										href="${pageContext.request.contextPath}/<c:out value="${allSubModuleList.subModuleMapping}" />"><c:out
												value="${allSubModuleList.subModulName}" /></a></li>

								</c:forEach>

							</ul></li>
					</c:forEach>

              
               <li class="menu-item-has-children dropdown">
                
                 <a  style="font-family:  'Merriweather', serif;"
									
										href="${pageContext.request.contextPath}/logout"><c:out
												value="Logout" /><i
								class="menu-icon fa fa-table"></i></a>
                 </li>
                


				</ul>

			</div>

		</nav>
	</aside>
	<!-- /#left-panel -->

	<!-- Left Panel -->
 <script>
  
 function selectSubMod(subModId, modId){
	
	 $.getJSON('${setSubModId}', {
		 subModId : subModId,
		 modId : modId,
						ajax : 'true'
					});
 }
 
 </script>
</body>
</html>