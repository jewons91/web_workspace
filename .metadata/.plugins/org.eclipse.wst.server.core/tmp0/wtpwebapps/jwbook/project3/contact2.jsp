<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>연락처 관리</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="project3/css/contact2.css">
</head>

<body class="bg-light">
	<div class="container mt-5">
		<!-- Login Section -->
		<div id="loginSection" class="row justify-content-center">
			<div class="col-md-6">
				<div class="card shadow-sm">
					<div class="card-body">
						<h2 class="mb-4 text-center">로그인</h2>
						<form id="loginForm">
							<div class="mb-3">
								<label for="user_id" class="form-label">아이디</label> <input
									type="text" id="user_id" name="user_id" class="form-control" required>
							</div>
							<div class="mb-3">
								<label for="password" class="form-label">비밀번호</label> <input
									type="password" id="password" name="password" class="form-control" required>
							</div>
							<button type="submit" class="btn btn-primary w-100">로그인</button>
						</form>
						<p class="mt-3 text-center">
							계정이 없으신가요? <a href="#" onclick="showRegister()">계정 만들기</a>
						</p>
					</div>
				</div>
			</div>
		</div>

		<!-- Register Section -->
		<div id="registerSection" class="row justify-content-center hidden">
			<div class="col-md-6">
				<div class="card shadow-sm">
					<div class="card-body">
						<h2 class="mb-4 text-center">계정 생성</h2>
						<form id="registerForm" action="contactControl?action=register" method="post">
							<div class="mb-3">
								<label for="regUser_id" class="form-label">아이디</label> <input
									type="text" id="regUser_id" name="user_id" class="form-control" required>
							</div>
							<div class="mb-3">
								<label for="regPassword" class="form-label">비밀번호</label> <input
									type="password" id="regPassword" name="password" class="form-control" required>
							</div>
							<div class="mb-3">
								<label for="regName" class="form-label">이름</label> <input
									type="text" id="regName" name="user_name" class="form-control" required>
							</div>
							<button type="submit" class="btn btn-primary w-100">확인</button>
						</form>
						<p class="mt-3 text-center">
							계정이 이미 있으신가요? <a href="#" onclick="showLogin()">로그인하러 가기</a>
						</p>
					</div>
				</div>
			</div>
		</div>

		<!-- Contact Management Section -->
		<div id="contactSection" class="hidden">
			<div class="d-flex justify-content-between align-items-center mb-3">
				<h2>ㅇㅇㅇ님의 연락처</h2>
				<button class="btn btn-danger" onclick="logout()">로그아웃</button>
			</div>
			<div class="mb-3">
				<input type="text" id="searchInput" class="form-control"
					placeholder="검색">
			</div>
			<table class="table table-striped table-hover table-bordered">
				<thead class="table-dark">
					<tr>
						<th>이름</th>
						<th>전화번호</th>
						<th>주소</th>
						<th>구분</th>
						<th class="action-header">
							<button class="btn btn-primary btn-sm"
								onclick="showAddContactModal()">추가</button>
						</th>
					</tr>
				</thead>
				<tbody id="contactsBody">
					<!-- Contacts will be populated here -->
				</tbody>
			</table>
		</div>
	</div>

	<!-- Add Contact Modal -->
	<div class="modal fade" id="addContactModal" tabindex="-1"
		aria-labelledby="addContactModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="addContactModalLabel">연락처 추가</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form id="addContactForm">
						<div class="mb-3">
							<label for="addName" class="form-label">이름</label> <input
								type="text" id="addName" class="form-control" required>
						</div>
						<div class="mb-3">
							<label for="addPhone" class="form-label">전화번호</label> <input
								type="text" id="addPhone" class="form-control" maxlength="11"
								required>
						</div>
						<div class="mb-3">
							<label for="addAddress" class="form-label">주소</label> <input
								type="text" id="addAddress" class="form-control" required>
						</div>
						<div class="mb-3">
							<label for="addCategory" class="form-label">구분</label> <select
								id="addCategory" class="form-control" required>
								<option value="가족">가족</option>
								<option value="친구">친구</option>
								<option value="회사">회사</option>
								<option value="기타">기타</option>
							</select>
						</div>
						<button type="submit" class="btn btn-primary">추가</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Edit Contact Modal -->
	<div class="modal fade" id="editContactModal" tabindex="-1"
		aria-labelledby="editContactModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="editContactModalLabel">연락처 수정</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form id="editContactForm">
						<input type="hidden" id="editId">
						<div class="mb-3">
							<label for="editName" class="form-label">이름</label> <input
								type="text" id="editName" class="form-control" required>
						</div>
						<div class="mb-3">
							<label for="editPhone" class="form-label">전화번호</label> <input
								type="text" id="editPhone" class="form-control" maxlength="11"
								required>
						</div>
						<div class="mb-3">
							<label for="editAddress" class="form-label">주소</label> <input
								type="text" id="editAddress" class="form-control" required>
						</div>
						<div class="mb-3">
							<label for="editCategory" class="form-label">구분</label> <select
								id="editCategory" class="form-control" required>
								<option value="가족">가족</option>
								<option value="친구">친구</option>
								<option value="회사">회사</option>
								<option value="기타">기타</option>
							</select>
						</div>
						<button type="submit" class="btn btn-primary">수정</button>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script type="text/javascript" src="project3/js/contact.js"></script>
</body>

</html>