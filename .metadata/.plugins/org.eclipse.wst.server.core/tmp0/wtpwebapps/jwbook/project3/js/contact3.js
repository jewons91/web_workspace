/** contact */
document.addEventListener('DOMContentLoaded', function() {
    loadContacts();
});
function loadContacts(searchTerm = '') {
    const contactsBody = document.getElementById('contactsBody');
    contactsBody.innerHTML = '';

    const formattedSearchTerm = searchTerm.toLowerCase();

    const filteredContacts = contacts.filter(contact => {
        const contactName = contact.name.toLowerCase();
        const contactPhone = contact.phone_number.toLowerCase();
        const contactAddress = contact.address.toLowerCase();
        const contactGubun = contact.gubun_name.toLowerCase();
        
        return contactName.includes(formattedSearchTerm) ||
               contactPhone.includes(formattedSearchTerm) ||
               contactAddress.includes(formattedSearchTerm) ||
               contactGubun.includes(formattedSearchTerm);
    });
    
    filteredContacts.forEach(contact => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${contact.name}</td>
            <td>${contact.phone_number}</td>
            <td>${contact.address}</td>
            <td>${contact.gubun_name}</td>
            <td class="action-buttons">
                <button class="btn btn-warning btn-sm" onclick="editContact(${contact.contact_id})">수정</button>
                <button class="btn btn-danger btn-sm" onclick="deleteContact(${contact.contact_id})">삭제</button>
            </td>
        `;
        contactsBody.appendChild(row);
    });
}

/*function validatePhoneNumber(phone) {
	const phoneRegex = /^010\d{8}$/;
	return phoneRegex.test(phone);
}

function formatPhoneNumber(phone) {
	return phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
}*/

document.getElementById('searchInput').addEventListener('input', function() {
	const searchTerm = this.value.trim();
	loadContacts(searchTerm);
});

function showAddContactModal() {
	const addContactModal = new bootstrap.Modal(document.getElementById('addContactModal'));
	addContactModal.show();
}

document.getElementById('addContactForm').addEventListener('submit', function() {
	const addContactModal = bootstrap.Modal.getInstance(document.getElementById('addContactModal'));
	addContactModal.hide();
	document.getElementById('addContactForm').reset();
});

function editContact(contact_id) {
	const contact = contacts.find(c => c.contact_id === contact_id);
	document.getElementById('editId').value = contact.contact_id;
	document.getElementById('editName').value = contact.name;
	document.getElementById('editPhone').value = contact.phone_number;
	document.getElementById('editAddress').value = contact.address;
	document.getElementById('editCategory').value = contact.gubun_name;
	const editContactModal = new bootstrap.Modal(document.getElementById('editContactModal'));
	editContactModal.show();
}

document.getElementById('editContactForm').addEventListener('submit', function(event) {
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