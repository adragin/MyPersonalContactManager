// scripts/app.js
const apiUrl = 'http://localhost:8080/MyPersonalContactManager/contacts';
const deleteContactUrl = 'http://localhost:8080/MyPersonalContactManager/contacts';

document.addEventListener("DOMContentLoaded", function() {
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            const contacts = data.response;
            const contactsList = document.getElementById('contacts-list');

            contacts.forEach(contact => {
                const listItem = document.createElement('li');
                listItem.className = 'contact-item';

                // Short view
                const shortView = document.createElement('div');
                shortView.className = 'contact-short';
                shortView.innerHTML = `
                    <p class="full_name">${contact.firstName} ${contact.lastName}</p>
                    <p>Phone: ${contact.phone}</p>
                    <p>Email: ${contact.email}</p>
                `;
                listItem.appendChild(shortView);

                // Detailed view
                const detailedView = document.createElement('div');
                detailedView.className = 'contact-details';
                detailedView.innerHTML = `
                    <p><strong>Birthday:</strong> ${contact.birthday ? contact.birthday : 'Not provided'}</p>
                    <p><strong>Address:</strong> ${contact.address}</p>
                    ${contact.photo ? `<img src="${contact.photo}" alt="Photo of ${contact.firstName} ${contact.lastName}" class="contact-photo">` : ''}
                    <p><strong>Create Date:</strong> ${new Date(contact.createDate).toLocaleString()}</p>
                    <button class="change-contact">Change contact</button>
                    <button class="delete-contact" data-id="${contact.id}">Delete contact</button>
                `;
                listItem.appendChild(detailedView);

                // Add click event to toggle view
                listItem.addEventListener('click', () => {
                    listItem.classList.toggle('expanded');
                });

                // Add delete functionality
                listItem.querySelector('.delete-contact').addEventListener('click', (event) => {
                    event.stopPropagation(); // Prevent toggle view on button click
                    const contactId = event.target.getAttribute('data-id');
                    deleteContact(contactId, listItem);
                });

                contactsList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error fetching contacts:', error));
});

function deleteContact(id, listItem) {
    fetch(`${deleteContactUrl}/${id}`, {
        method: 'DELETE'
    })
        .then(response => response.json())
        .then(result => {
            if (result) {
                listItem.remove();
                alert('Contact deleted successfully.');
            } else {
                alert('Failed to delete contact.');
            }
        })
        .catch(error => console.error('Error deleting contact:', error));
}
