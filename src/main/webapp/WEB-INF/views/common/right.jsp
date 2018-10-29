<%@page pageEncoding="UTF-8"%>


<div id="right-panel" class="right-panel">

	<header id="header" class="header" style="height: 58px;">

		<div class="header-menu">

			<div class="col-sm-7">
				<a id="menuToggle" class="menutoggle pull-left"><i
					class="fa fa fa-tasks"></i></a>
				<div class="header-left">



					<div class="dropdown for-notification">
						<button class="btn btn-secondary dropdown-toggle" type="button"
							id="notification" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false">
							<i class="fa fa-bell"></i> <span class="count bg-danger">5</span>
						</button>
						<div class="dropdown-menu" aria-labelledby="notification">
							<p class="red">You have 3 Notification</p>
							<a class="dropdown-item media bg-flat-color-1" href="#"> <i
								class="fa fa-check"></i>
								<p>Server #1 overloaded.</p>
							</a> <a class="dropdown-item media bg-flat-color-4" href="#"> <i
								class="fa fa-info"></i>
								<p>Server #2 overloaded.</p>
							</a> <a class="dropdown-item media bg-flat-color-5" href="#"> <i
								class="fa fa-warning"></i>
								<p>Server #3 overloaded.</p>
							</a>
						</div>
					</div>

					<div class="dropdown for-message">
						<button class="btn btn-secondary dropdown-toggle" type="button"
							id="message" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false">
							<i class="ti-email"></i> <span class="count bg-primary">9</span>
						</button>
						<div class="dropdown-menu" aria-labelledby="message">
							<p class="red">You have 4 Mails</p>
							<a class="dropdown-item media bg-flat-color-1" href="#"> <span
								class="photo media-left"><img alt="avatar"
									src="${pageContext.request.contextPath}/resources/images/avatar/1.jpg"></span>
								<span class="message media-body"> <span
									class="name float-left">Jonathan Smith</span> <span
									class="time float-right">Just now</span>
									<p>Hello, this is an example msg</p>
							</span>
							</a> <a class="dropdown-item media bg-flat-color-4" href="#"> <span
								class="photo media-left"><img alt="avatar"
									src="${pageContext.request.contextPath}/resources/images/avatar/2.jpg"></span>
								<span class="message media-body"> <span
									class="name float-left">Jack Sanders</span> <span
									class="time float-right">5 minutes ago</span>
									<p>Lorem ipsum dolor sit amet, consectetur</p>
							</span>
							</a> <a class="dropdown-item media bg-flat-color-5" href="#"> <span
								class="photo media-left"><img alt="avatar"
									src="${pageContext.request.contextPath}/resources/images/avatar/3.jpg"></span>
								<span class="message media-body"> <span
									class="name float-left">Cheryl Wheeler</span> <span
									class="time float-right">10 minutes ago</span>
									<p>Hello, this is an example msg</p>
							</span>
							</a> <a class="dropdown-item media bg-flat-color-3" href="#"> <span
								class="photo media-left"><img alt="avatar"
									src="${pageContext.request.contextPath}/resources/images/avatar/4.jpg"></span>
								<span class="message media-body"> <span
									class="name float-left">Rachel Santos</span> <span
									class="time float-right">15 minutes ago</span>
									<p>Lorem ipsum dolor sit amet, consectetur</p>
							</span>
							</a>
						</div>
					</div>
				</div>
			</div>

			<div class="col-sm-5">
				<div class="user-area dropdown float-right">

					<div align="right" style="align-self: center; align-content: center; align-items: center; vertical-align: middle;">

						<a href="?lang=en" style="color: #000;"> English | </a> <a
							href="?lang=mr" style="color: #000;">मराठी </a>
 
						&nbsp;&nbsp;&nbsp;&nbsp; <a href="#" class="dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<img class="user-avatar rounded-circle"
							src="${pageContext.request.contextPath}/resources/images/admin.jpg"
							alt="User Avatar">
						</a>

						<div class="user-menu dropdown-menu">
							<a class="nav-link" href="#"><i class="fa fa- user"></i>My
								Profile</a> <a class="nav-link" href="#"><i class="fa fa- user"></i>Notifications
								<span class="count">13</span></a> <a class="nav-link" href="#"><i
								class="fa fa -cog"></i>Settings</a> <a class="nav-link"
								href="${pageContext.request.contextPath}/logout"><i
								class="fa fa-power -off"></i>Logout</a>
						</div>
					</div>
				</div>




			</div>
		</div>

	</header>
	<!-- /header -->