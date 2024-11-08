/**
 * 
 *//** contact */
function showAddContactModal() {
	const addContactModal = new bootstrap.Modal(document.getElementById('addContactModal'));
	addContactModal.show();
}

document.getElementById('addContactForm').addEventListener('submit', function() {
	const addContactModal = bootstrap.Modal.getInstance(document.getElementById('addContactModal'));
	addContactModal.hide()
});

function editContact(button) {
	const tr = button.closest('tr');

	const contactId = tr.getAttribute('data-id');
	const contactName = tr.getAttribute('data-name');
	const contactPhone = tr.getAttribute('data-phone');
	const contactAddress = tr.getAttribute('data-address');
	const contactCategory = tr.getAttribute('data-category');
	
	document.getElementById('editId').value = contactId;
    document.getElementById('editName').value = contactName;
    document.getElementById('editPhone').value = contactPhone;
    document.getElementById('editAddress').value = contactAddress;
    document.getElementById('editCategory').value = contactCategory;

    const editContactModal = new bootstrap.Modal(document.getElementById('editContactModal'));
    editContactModal.show();
}

document.getElementById('editContactForm').addEventListener('submit', function() {
	const editContactModal = bootstrap.Modal.getInstance(document.getElementById('editContactModal'));
	editContactModal.hide();
});