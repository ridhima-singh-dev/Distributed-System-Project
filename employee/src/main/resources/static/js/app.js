let email = 'kamal@ucdconnect.ie';

// Call below function on index.html load
getAppliedJobs();

function getAppliedJobs() {
    fetch(`http://localhost:8080/getAppliedJobs/${email}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            populateTable(data);
        })
        .catch(error => console.log('Error:', error));
}

function populateTable(data) {
    const tableBody = document.getElementById('jobs'); 
    // Loop through the data and create table rows
    data.forEach(item => {
        console.log(item.companyName)
        const newRow = document.createElement('tr'); // Create a new table row
        // Create table cells and populate data
        newRow.innerHTML = `
        <td>#${item.jobID}</td>
        <td>
            <span class="img-thumb">
                ${getCompanyIcon(item.companyName)}
                <span class="ml-2">${capitalizeFirstLetter(item.companyName)}</span>
            </span>
        </td>
        <td>31/12/2023</td>
        <td>Software Engineer</td>
        <td>Remote</td>
        <td>$${parseInt(item.salary)}</td>
        <td>Skills</td>
        <td><label class="mb-0 badge badge-primary" title="" data-original-title="Pending">View Detail</label></td>
        `;
        tableBody.appendChild(newRow);
    });
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function getCompanyIcon(company) {
    switch (company) {
        case "google":
            return "<i class='fab fa-google' style='border-radius: 50%; font-size: 30px;'></i>";
        case "facebook":
            return "<i class='fab fa-facebook' style='border-radius: 50%; font-size: 30px;'></i>";
        case "amazon":
            return "<i class='fab fa-amazon' style='border-radius: 50%; font-size: 30px;'></i>";
        case "apple":
            return "<i class='fab fa-apple' style='border-radius: 50%; font-size: 30px;'></i>";
        case "netflix":
            return "<svg xmlns='http://www.w3.org/2000/svg' x='0px' y='0px' width='30' height='30' viewBox='0 0 50 50'>" +
            "<path d='M27,6v10.75L23.48,6H23h-8.33H14v38.15l9-1.28V32.58l3.24,10.34l6.14,0.72L36,44.15V6H27z M29,8h5v30.13l-5-15.27V8z M17.39,8h4.64L23,10.96l4,12.22l2,6.12l4.06,12.4l-0.54-0.06l-4.76-0.56L27,38.66L23,25.9l-2-6.38L17.39,8z M21,41.13l-5,0.72 V10.24l5,15.96V41.13z'></path>" +
            "</svg>";
    }
}
