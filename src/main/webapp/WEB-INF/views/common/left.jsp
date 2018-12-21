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


					<%-- <li class="menu-item-has-children dropdown"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="true"> <i
							class="menu-icon fa fa-table"></i> Transaction
					</a>
						<ul class="sub-menu children dropdown-menu ">
							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showAddEnquiry">Add
									Enquiry</a></li>
							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showEnqList">
									Enquiry List</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showQuotations">View
									Quotations</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showQuotationsCustWise">View
									Quotations Serially</a></li>


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showAddOrder">Add
									Order</a></li>
							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showOrderList">
									Orders List</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showAddChalan">Add
									Chalan</a></li>
							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showChalanList">
									Chalan List</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showDispatchSheet">
									Order Dispatch</a></li>
							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showBill"> Add Bill</a></li>
							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showProdPlanList">
									Production Plan List</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showProdHeadReport">
									Production Report</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showRMHeadReport">
									Raw Material Report</a></li>








							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showAddWeighing">Add
									Weighing</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showWeighList">Weighing
									List</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showWeighingList">All
									Weighing List</a></li>


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showAddPReading">Add
									Poklen Reading</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showPoklenReadingList">Pokle
									Reading List</a></li>




						</ul></li>

					<li class="menu-item-has-children dropdown"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="true"> <i
							class="menu-icon fa fa-table"></i> Production Crushing Plant
					</a>
						<ul class="sub-menu children dropdown-menu ">

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showAddMatIssueContractor">Add
									Material Issue Contractor</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showMatIssueContractorList">Material
									Issue Contractor List</a></li>


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showAddMatIssueVehicle">Add
									Material Issue Vehicle</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showMatIssueVehicleList">Material
									Issue Vehicle List</a></li>


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showContractReport">Contractorwise
									Report</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showVehicleReport">Vehiclewise
									Report</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showPoklenReport">Poklenwise
									Report</a></li>

						</ul></li>


					<li class="menu-item-has-children dropdown"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="true"> <i
							class="menu-icon fa fa-table"></i>Marketing Report
					</a>
						<ul class="sub-menu children dropdown-menu ">


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showQuotationPendingReport">Quotation
									Pending Report</a></li>


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showPoPendingReport">PO
									Pending Report</a></li>


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showChalanPendingReport">Chalan
									Pending Report</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showBillPendingReport">Bill
									Pending Report</a></li>

						</ul></li>


					<li class="menu-item-has-children dropdown"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="true"> <i
							class="menu-icon fa fa-table"></i>Bill Report
					</a>
						<ul class="sub-menu children dropdown-menu ">


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showBillwiseReport">Billwise
									Report</a></li>


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showCustomerwiseReport">Customerwise
									Bill Report</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showTaxwiseReport">Taxwise
									Bill Report</a></li>


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showItemwiseReport">Itemwise
									Bill Report</a></li>

							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showMonthwiseReport">Monthwise
									Bill Report</a></li>


							<li class="active"><i class="fa fa-puzzle-piece"></i><a
								href="${pageContext.request.contextPath}/showDatewiseBillReport">Datewise
									Bill Report</a></li>


						</ul></li>
					<li class="menu-item-has-children dropdown"><a
						href="${pageContext.request.contextPath}/editMyProfile/1"> <i
							class="menu-icon fa fa-table"></i> My Profile
					</a></li> --%>



					<%-- 	
				<li class="menu-item-has-children dropdown"><a
					href="${pageContext.request.contextPath}/editHubUser/1"> <i
						class="menu-icon fa fa-table"></i> <spring:message
							code="label.userProfile" />
				</a></li> --%>

					<%-- 	<li class="menu-item-has-children dropdown"><a
					href="${pageContext.request.contextPath}/editHubUser/1"
					class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="true"> <i class="menu-icon fa fa-table"></i> <spring:message
							code="label.userProfile" /> --%>

					<%-- 	 --%>
				</ul>

			</div>
			<!-- /.navbar-collapse -->
		</nav>
	</aside>
	<!-- /#left-panel -->

	<!-- Left Panel -->

</body>
</html>