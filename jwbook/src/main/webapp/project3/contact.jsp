<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>연락처 관리</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="project3/css/contact2.css">
</head>
<body>
	<div id="contactSection">
		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2>${sessionScope.user_name}님의연락처</h2>
			<form action="contactControl?action=logout" method="post"
				style="display: inline;">
				<button class="btn btn-danger">로그아웃</button>
			</form>
		</div>
		<div class="mb-3">
			<form action="contactControl?action=findByType" method="post"
				class="row g-3">
				<div class="col-md-4">
					<input type="text" id="searchInput" name="searchInput"
						class="form-control"
						placeholder="검색할 내용을 입력하세요.-전체 목록을 볼려면 입력없이 검색">
				</div>
				<div class="col-md-4">
					<select id="searchType" name="searchType" class="form-select">
						<option value="name">이름</option>
						<option value="phone_number">전화번호</option>
						<option value="address">주소</option>
						<option value="gubun_name">구분</option>
					</select>
				</div>
				<div class="col-md-4">
					<button type="submit" class="btn btn-primary w-100">검색</button>
				</div>
			</form>
		</div>
		<c:if test="${duplicated != null }">
			<div class="alert alert-danger alert-dismissible fade show mt-3">
				${duplicated}
				<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
			</div>
		</c:if>
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
				<c:forEach var="c" items="${contacts }">
					<tr data-id="${c.contact_id}" data-name="${c.name}"
						data-phone="${c.phone_number}" data-address="${c.address}"
						data-category="${c.gubun_name}">
						<td>${c.name }</td>
						<td>${c.phone_number }</td>
						<td>${c.address }</td>
						<td>${c.gubun_name }</td>
						<td class="action-buttons">
							<button class="btn btn-warning btn-sm"
								onclick="editContact(this)">수정</button>
							<form action="contactControl?action=deleteContact" method="post"
								style="display: inline"
								onsubmit="return confirm('정말 이 연락처를 삭제하시겠습니까?')">
								<input type="hidden" name="contact_id" value="${c.contact_id }">
								<button type="submit" class="btn btn-danger btn-sm">삭제</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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
					<form id="addContactForm"
						action="contactControl?action=insertContact" method="post">
						<div class="mb-3">
							<label for="addName" class="form-label">이름</label> <input
								type="text" id="addName" name="name" class="form-control"
								required>
						</div>
						<div class="mb-3">
							<label for="addPhone" class="form-label">전화번호</label> <input
								type="text" id="addPhone" name="phone_number"
								class="form-control" required>
						</div>
						<div class="mb-3">
							<label for="addAddress" class="form-label">주소</label> <input
								type="text" id="addAddress" name="address" class="form-control"
								required>
						</div>
						<div class="mb-3">
							<label for="addCategory" class="form-label">구분</label> <select
								id="addCategory" name="gubun_name" class="form-control" required>
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
					<form id="editContactForm"
						action="contactControl?action=updateContact" method="post">
						<input type="hidden" id="editId" name="contact_id">
						<div class="mb-3">
							<label for="editName" class="form-label">이름</label> <input
								type="text" id="editName" name="name" class="form-control"
								required>
						</div>
						<div class="mb-3">
							<label for="editPhone" class="form-label">전화번호</label> <input
								type="text" id="editPhone" name="phone_number"
								class="form-control" maxlength="11" required>
						</div>
						<div class="mb-3">
							<label for="editAddress" class="form-label">주소</label> <input
								type="text" id="editAddress" name="address" class="form-control"
								required>
						</div>
						<div class="mb-3">
							<label for="editCategory" class="form-label">구분</label> <select
								id="editCategory" name="gubun_name" class="form-control"
								required>
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
	<script>
		const contacts = $
		{
			contacts
		};
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script type="text/javascript" src="project3/js/contact.js"></script>
</body>
</html>