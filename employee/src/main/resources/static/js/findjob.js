getAllJobs();
var dataCopy = []
var jobData = {
    
};

var skillArray = []

var selectedskills = []

function getAllJobs() {
    callLoader()
    fetch(`http://localhost:8080/findAllJobs`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            populateCompany(data);
            populateSkillArray(data);
            dataCopy = data;
            populateTable(data);
            setTimeout(removeLoader, 1000);
            
        })
        .catch(error => {
            console.log('Error:', error)
            callError()
        });
}

function populateCompany(data){
    console.log(data);
    data.forEach((el, i)=>{

        if(el.companyName in jobData){
            jobData[el.companyName.toLowerCase()] = {
                data: [...jobData[el.companyName].data, el],
        count: jobData[el.companyName].data.length()
            }
        }else{
            jobData[el.companyName.toLowerCase()] = {
                data : [el],
                count : 1
            }
        }
    })
    populateCompanyFilter(jobData)
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
                ${getCompanyIcon(item.companyName.toLowerCase())}
                <span class="ml-2">${item.companyName}</span>
            </span>
        </td>
        <td>${capitalizeFirstLetter(item.title)}</td>
        <td>${capitalizeFirstLetter(item.location)}</td>
        <td>$${item.salary}</td>
        <td>
            <div class="dropdown">
                <button class="dropdown-toggle mb-0 badge badge-primary" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Skills
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                    ${item.skills.map((i) => `<button class="dropdown-item" type="button">${i}</button>`).join('')}
                </div>
            </div>
        </td>
        <td><label class="mb-0 badge badge-primary view-detail" title="" data-original-title="Pending">View</label></td>
        <td><label class="mb-0 badge badge-primary view-detail" title="" data-original-title="Pending">Apply</label></td>
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

function filterCompany(company){
    document.getElementById('jobs').innerHTML = '';
    if (company == 'facebook'){
        populateTable(jobData.facebook.data)
    }else if(company == 'amazon'){
        populateTable(jobData.amazon.data)

    }else if(company == 'apple'){
        populateTable(jobData.apple.data)
        
    }else if(company == 'netflix'){
        populateTable(jobData.netflix.data)
        
    }else if(company == 'google'){
        populateTable(jobData.google.data)
        
    }else{
        populateTable(dataCopy)
    }
}

function populateSkillArray(data){
    data.map(el=>{
        el.skills.forEach((skill,i)=>{
            if (skillArray.indexOf(skill.toLowerCase()) == -1){
                skillArray.push(skill.toLowerCase());
            }
        })
    })

    let skillTable = document.getElementById('skills-table');
    skillArray.forEach((el,i)=>{
        let newRow =  document.createElement('li');
        newRow.innerHTML = `
        <div class="int-checkbox">
            <input type="checkbox" id="${el}" name="remember" value="${el}" onclick="filterBySkill('${el}')">
            <label for="${el}">${capitalizeFirstLetter(el)}</label>
        </div>
    `
    skillTable.appendChild(newRow);
    })
}


function filterBySkill(skill){
    if (selectedskills.indexOf(skill) == -1){
        selectedskills.push(skill)
    }else{
        skillIndex = selectedskills.indexOf(skill);
        selectedskills.splice(skillIndex, 1);
    }

    if(selectedskills.length !== 0){
        let querystring = ""
    selectedskills.forEach((el,i)=>{
        let skillname = capitalizeFirstLetter(el)
        let query = `skills=${skillname}${i == (selectedskills.length -1)? '' :'&' }`;
        querystring += query
        
    })
    console.log(querystring)

    fetch(`http://localhost:8080/findJobsBySkills?${querystring}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('jobs').innerHTML = '';
            populateTable(data);
            
        })
        .catch(error => {
            callError()
            console.log('Error:', error)});
    }else{
        document.getElementById('jobs').innerHTML = '';
        populateTable(dataCopy)
    }
}


function filterByTitle(){
    var inputValue = document.getElementById("titleSearch").value;
    fetch(`http://localhost:8080/findJobsByTitle?title=${inputValue}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById('jobs').innerHTML = '';
            populateTable(data);
            
        })
        .catch(error => console.log('Error:', error));
}


function populateCompanyFilter(data){
    let companyFilter = document.getElementById("company-filter");
    Object.keys(data).forEach((el,i)=>{
        console.log(el, "Job")
        let newRow =  document.createElement('li');
        newRow.addEventListener('click', function() {
            // Your onclick logic here
            filterCompany(el)
        });
        newRow.innerHTML = `
        <a href="javascript:;"><svg xmlns="http://www.w3.org/2000/svg" width="12px" height="8px">
                                        <path fill-rule="evenodd" fill=" " d="M0.038,4.720 L6.164,4.710 C6.558,4.710 6.878,4.392 6.878,3.999 L6.878,2.016 L9.967,3.999 L5.777,6.688 C5.445,6.901 5.349,7.342 5.563,7.673 C5.777,8.004 6.219,8.099 6.551,7.886 L11.673,4.597 C11.877,4.466 12.000,4.241 12.000,3.999 C12.000,3.756 11.877,3.531 11.673,3.400 L6.551,0.112 C6.331,-0.030 6.051,-0.040 5.822,0.085 C5.592,0.210 5.449,0.449 5.449,0.710 L5.449,3.286 L0.000,3.286 "></path>
                                    </svg> ${capitalizeFirstLetter(el)}</a><span>${data[el].count}</span>
    `
    companyFilter.appendChild(newRow);
})
}

function callLoader (){
    let loaderBody = document.getElementById("loader_body");
    loaderBody.innerHTML = `
    <div class="loader">
            <div class="spinner">
              <img src="img/loader.gif" alt="">
            </div> 
          </div>
    `
}

function removeLoader (){
    let loaderBody = document.getElementById("loader_body");
    loaderBody.innerHTML =''
}

function callError(){
    let errorBody = document.getElementById("error_body");
    errorBody.innerHTML = `
    <div class="loader">
    <img src="img/error.png" style="width: 400px;">
    </div>`
}


// 