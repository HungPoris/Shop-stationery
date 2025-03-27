/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function showAddressForm() {
    document.getElementById('addressForm').style.display = 'block';
}

function hideAddressForm() {
    document.getElementById('addressForm').style.display = 'none';
}

function submitForm() {
    // Logic to submit the form data to the server
    hideAddressForm();
}
