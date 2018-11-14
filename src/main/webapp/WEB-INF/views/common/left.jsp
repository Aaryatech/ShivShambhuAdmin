
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
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/home"> <i
						class="menu-icon fa fa-dashboard"></i> Dashboard
				</a></li>

				<!--                     <h3 class="menu-title">UI elements</h3>/.menu-title
 -->
				<li class="menu-item-has-children dropdown"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="true"> <i class="menu-icon fa fa-table"></i>
						Masters
				</a>
					<ul class="sub-menu children dropdown-menu ">
						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showAddPlant">Add
								Plant</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showAddDocTerm">Add
								Doc Term</a></li>


						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showAddCustomer">Add
								Customer</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showAddItem">Add
								Item</a></li>


						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showAddProject">Add
								Project</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showAddCompany">Add
								Company</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showCompList">
								Company List</a></li>


						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showPlantList">
								Plant List</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showCustList">
								Customer List</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showItemList"> Item
								List</a></li>



					</ul></li>




				<li class="menu-item-has-children dropdown"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="true"> <i class="menu-icon fa fa-table"></i>
						Transaction
				</a>
					<ul class="sub-menu children dropdown-menu ">
						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showAddEnquiry">Add
								Enquiry</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showQuotations">View
								Quotations</a></li>



					</ul></li>

				<%-- 	<li class="menu-item-has-children dropdown"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="true"> <i class="menu-icon fa fa-table"></i>
						Work
				</a>
					<ul class="sub-menu children dropdown-menu ">

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showAddWorkHeader">Add
								Work</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showWorkHeadList">
								View Added Work</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showWorkList">Work
								List</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showUpdatePayment">Update
								Payment</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showUserAllocation">User
								Allocation</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showDocInOffice">
								Document In Office</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showDocSubmitAtRto">
								Document Submit At RTO</a></li>

						<li class="active"><i class="fa fa-puzzle-piece"></i><a
							href="${pageContext.request.contextPath}/showActualDocToCust">
								Handover Actual doc to Cust</a></li>



					</ul></li>
 --%>

				<li class="menu-item-has-children dropdown"><a
					href="${pageContext.request.contextPath}/editMyProfile/1"> <i
						class="menu-icon fa fa-table"></i> My Profile
				</a></li>



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
				</a>
				<%-- 	 --%>
			</ul>

		</div>
		<!-- /.navbar-collapse -->
	</nav>
</aside>
<!-- /#left-panel -->

<!-- Left Panel -->

