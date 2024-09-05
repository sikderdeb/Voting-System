let epicId= '';

function generateOtp() {
    const epic = document.getElementById('epic').value;
    const dob = document.getElementById('dob').value;
    if (epic === '' || dob === '') {
        alert('Please fill in all fields.');
        return;
    }


    const url = `http://localhost:8081/api/generateOtp?EpicNo=${encodeURIComponent(epic)}&Dob=${encodeURIComponent(dob)}`;
    fetch(url, {
        method: 'POST',
    })
        .then(response => {
            if (response.ok) {
                epicId = epic;
                document.getElementById('initial-screen').style.display = 'none';
                document.getElementById('otp-screen').style.display = 'block';
                console.log('OTP sent successfully');

            } else if (response.status === 401) {
                alert('You have already voted.');
            } else if (response.status === 404) {
                alert('Wrong Credentials.');
            } else {
                alert('An unexpected error occurred.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });

}

function verifyOtp() {
    const otp = document.getElementById('otp').value;
    if (otp === '') {
        alert('Please enter the OTP.');
        return;
    }
    const url = `http://localhost:8081/api/validateOtp?EpicNo=${encodeURIComponent(epicId)}&Otp=${encodeURIComponent(otp)}`;
    fetch(url, {
        method: 'POST',
    })
        .then(response => {
            if (response.ok) {
                console.log('OTP validation successful');
                document.getElementById('otp-screen').style.display = 'none';
                document.getElementById('voting-screen').style.display = 'block';
            } else if (response.status === 404) {
                alert('Failed to validate OTP');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });

}

function vote(option) {
    const url = `http://localhost:8081/api/giveVote?Vote=${encodeURIComponent(option)}`;
    fetch(url, {
        method: 'POST',
    })
        .then(response => {
            if (response.ok) {
                console.log('Vote submitted successfully');
                document.getElementById('status').innerText = 'Vote submitted successfully';
            } else {
                console.log('Failed to submit vote');
                document.getElementById('status').innerText = 'Failed to submit vote';
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
    alert('You voted for ' + option);
    document.getElementById('voting-screen').style.display = 'none';
    document.getElementById('confirmation-screen').style.display = 'block';
}