/** contact */
let contacts = [
	{ id: 1, name: "서제원", phone: "010-7707-3012", address: "서울시 노원구", category: "가족" },
	{ id: 2, name: "데이터", phone: "010-1234-5678", address: "서울시 강남구", category: "회사" }
];

function showLogin() {
	document.getElementById('loginSection').classList.remove('hidden');
	document.getElementById('registerSection').classList.add('hidden');
	document.getElementById('contactSection').classList.add('hidden');
}

function showRegister() {
	document.getElementById('loginSection').classList.add('hidden');
	document.getElementById('registerSection').classList.remove('hidden');
	document.getElementById('contactSection').classList.add('hidden');
}

function showContacts() {
	document.getElementById('loginSection').classList.add('hidden');
	document.getElementById('registerSection').classList.add('hidden');
	document.getElementById('contactSection').classList.remove('hidden');
	loadContacts();
}

function loadContacts(searchTerm = '') {
	const contactsBody = document.getElementById('contactsBody');
	contactsBody.innerHTML = '';

	const formattedSearchTerm = searchTerm.replace(/-/g, '');

	const filteredContacts = contacts.filter(contact =>
		contact.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
		contact.phone.includes(searchTerm) ||
		contact.phone.replace(/-/g, '').includes(formattedSearchTerm) ||
		contact.address.toLowerCase().includes(searchTerm.toLowerCase()) ||
		contact.category.includes(searchTerm)
	);

	filteredContacts.forEach(contact => {
		const row = document.createElement('tr');
		row.innerHTML = `
                <td>${contact.name}</td>
                <td>${contact.phone}</td>
                <td>${contact.address}</td>
                <td>${contact.category}</td>
                <td class="action-buttons">
                   	<button class="btn btn-warning btn-sm" onclick="editContact(${contact.id})">수정</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteContact(${contact.id})">삭제</button>
                </td>
            `;
		contactsBody.appendChild(row);
	});
}

function validatePhoneNumber(phone) {
	const phoneRegex = /^010\d{8}$/;
	return phoneRegex.test(phone);
}

function formatPhoneNumber(phone) {
	return phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
}

document.getElementById('searchInput').addEventListener('input', function() {
	const searchTerm = this.value.trim();
	loadContacts(searchTerm);
});

function showAddContactModal() {
	const addContactModal = new bootstrap.Modal(document.getElementById('addContactModal'));
	addContactModal.show();
}

document.getElementById('addContactForm').addEventListener('submit', function(event) {
	event.preventDefault();
	const phone = document.getElementById('addPhone').value;
	if (!validatePhoneNumber(phone)) {
		alert("전화번호는 01012345678 형식으로 입력해주세요.");
		return;
	}
	const newContact = {
		id: contacts.length + 1,
		name: document.getElementById('addName').value,
		phone: formatPhoneNumber(phone),
		address: document.getElementById('addAddress').value,
		category: document.getElementById('addCategory').value
	};
	contacts.push(newContact);
	loadContacts();
	const addContactModal = bootstrap.Modal.getInstance(document.getElementById('addContactModal'));
	addContactModal.hide();
	document.getElementById('addContactForm').reset();
});

function editContact(contactId) {
	const contact = contacts.find(c => c.id === contactId);
	document.getElementById('editId').value = contact.id;
	document.getElementById('editName').value = contact.name;
	document.getElementById('editPhone').value = contact.phone.replace(/-/g, '');
	document.getElementById('editAddress').value = contact.address;
	document.getElementById('editCategory').value = contact.category;
	const editContactModal = new bootstrap.Modal(document.getElementById('editContactModal'));
	editContactModal.show();
}

document.getElementById('editContactForm').addEventListener('submit', function(event) {
	event.preventDefault();
	const contactId = parseInt(document.getElementById('editId').value);
	const phone = document.getElementById('editPhone').value;
	if (!validatePhoneNumber(phone)) {
		alert("전화번호는 01012345678 형식으로 입력해주세요.");
		return;
	}
	const updatedContact = {
		id: contactId,
		name: document.getElementById('editName').value,
		phone: formatPhoneNumber(phone),
		address: document.getElementById('editAddress').value,
		category: document.getElementById('editCategory').value
	};
	contacts = contacts.map(contact => contact.id === contactId ? updatedContact : contact);
	loadContacts();
	const editContactModal = bootstrap.Modal.getInstance(document.getElementById('editContactModal'));
	editContactModal.hide();
});

function deleteContact(contactId) {
	const isConfirmed = confirm("정말 이 연락처를 삭제하시겠습니까?");
	if (isConfirmed) {
		contacts = contacts.filter(contact => contact.id !== contactId);
		loadContacts();
	}
}

function logout() {
	console.log('로그아웃');
	showLogin();
}

document.getElementById('loginForm').addEventListener('submit', function(event) {
	event.preventDefault();
	console.log('로그인');
	document.getElementById('loginForm').reset();
	showContacts();
});

document.getElementById('registerForm').addEventListener('submit', function(event) {
	event.preventDefault();
	console.log('등록 완료');
	document.getElementById('registerForm').reset();
	showLogin();
});

/*document.addEventListener('DOMContentLoaded', showLogin);*/