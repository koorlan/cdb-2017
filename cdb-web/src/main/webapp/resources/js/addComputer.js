//Control add form


function validateName (name) {
	reg = /^[\w\d\. \-\(\)\[\]\u00C0-\u00ff]*$/;

	if( name === "" || !reg.test(name)) {
		$('#alert-name-danger').show();
		return false;
	} else {
		$('#alert-name-danger').hide();
		return true;
	}
}

function validateIntroduced( date ) {
	if( validateDate(date) ) {
		$('#alert-introduced-danger').hide();
	} else {
		$('#alert-introduced-danger').show();
	}
	
}

function validateDiscontinued( date ) {
	if( validateDate(date) ) {
		$('#alert-discontinued-danger').hide();
	} else {
		$('#alert-discontinued-danger').show();
	}
}
	
function validateDate (date) {
	if( date === "") {
		return true;
	}

	try {
		d = new Date(date).toISOString();
		return true;
	} catch (e) {
		return false
	}
};

function validateIntroducedBeforeDiscontinued (date1,date2) {
	if ( date1 === "" || date2 === "") {
		return true;
	}
	
	introduced;
	discontinued;
	try {
	 introduced = new Date(date1).toISOString();
	 discontinued = new Date(date2).toISOString();
	} catch (e) {
		return false;
	}
	
	if( introduced  >= discontinued ) {
		$('#alert-date-danger').show();
		return false;
	} else {
		$('#alert-date-danger').hide();
		return true;
	}
	
}

function validateForm () {
	
	validate = validateName($('#computerName').val());
	console.log(validate);
	validate &= validateDate($('#introduced').val());
	console.log(validate);
	validate &= validateDate($('#discontinued').val());
	console.log(validate);
	validate &= validateIntroducedBeforeDiscontinued($('#introduced').val(), $('#discontinued').val());
	console.log(validate);
	return validate === 1;
}



$('#computerName').keyup( function() {
	validateName($('#computerName').val());
});
$('#computerName').change( function() {
	validateName($('#computerName').val());
});

$('#introduced').keyup( function() {
	validateIntroduced($('#introduced').val());
	validateIntroducedBeforeDiscontinued($('#introduced').val(), $('#discontinued').val());
});

$('#introduced').change( function() {
	validateIntroduced($('#introduced').val());
	validateIntroducedBeforeDiscontinued($('#introduced').val(), $('#discontinued').val());
});

$('#discontinued').keyup( function() {
	validateDiscontinued($('#discontinued').val());
	validateIntroducedBeforeDiscontinued($('#introduced').val(), $('#discontinued').val());
});


$('#discontinued').change( function() {
	validateDiscontinued($('#discontinued').val());
	validateIntroducedBeforeDiscontinued($('#introduced').val(), $('#discontinued').val());
});
