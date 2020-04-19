$(() => {
	function getCookie(cname) {
		  var name = cname + "=";
		  var ca = document.cookie.split(';');
		  for(var i = 0; i < ca.length; i++) {
		    var c = ca[i];
		    while (c.charAt(0) == ' ') {
		      c = c.substring(1);
		    }
		    if (c.indexOf(name) == 0) {
		      return c.substring(name.length, c.length);
		    }
		  }
		  return "";
		}
	
	var courseId;
	$("#sel1").on("change", () => {
		courseId = $("#sel1").find(":selected").attr("id");
		$.get("load-course.htm?courseId=" + courseId, (response) => {
			console.log(response);
			document.cookie = "courseId=" + courseId; //
		})
	})
//	$("#sel1 option[id='" + document.cookie.split(";")[0] + "']").attr("selected", "selected");
	$("#sel1 option[id='" + getCookie("courseId") + "']").attr("selected", "selected");
	
    let elements = {
            login_email: $("#defaultForm-email"),
            login_password: $("#defaultForm-pass"),
            signup_email: $("#orangeForm-email"),
            signup_password: $("#orangeForm-pass"),
            signup_confirm_password: $("#orangeForm-confpass"),
            signup_phone: $("#orangeForm-phone"),
            signup_roles: $(".signup_role_radio"),
            signup_name: $("#orangeForm-name"),
            signup_identification_number: $("#orangeForm-idnum"),
            signup_submit: $("#signup_submit"),
            signup_form: $("#signup_form"),
            login_form: $("#login_form"),
        };
	elements.signup_form.on("submit", (ev) => {
		ev.preventDefault();
		console.log(elements);
		var flag = false;
		var role = $("input[name='su_role']:checked").val();
		$.get(
				"email-existance.htm?identificationNumber=" + elements.signup_identification_number.val() + "&role=" + role + "&email=" + elements.signup_email.val() , 
				(response) => {
					var errors = "";
					console.log(response);
					if(response === "exist") {
						errors += "\n Email or Identification Number already exist!";
					} else {
						flag = true;
					}
					if(!flag) {
						$("#signup_errors").html("Errors: " + errors);
					} else {	
						if(elements.signup_confirm_password.val() !== elements.signup_password.val()) {
							$("#signup_errors").html("Errors: Password and Confirm Password does not match.");
						} else {							
							$("#signup_errors").html("");
							elements.signup_form.unbind("submit");
							elements.signup_form.submit();
						}
					}
			
		})
	})
	
	var flag = false;
	var loginRole = $("input[name='login_role']:checked").val();
	
	elements.login_form.on("submit", (ev) => {
		ev.preventDefault();
		console.log(elements);
		var flag = false;
		var role = $("input[name='login_role']:checked").val();
		$.get(
				"email-password-check.htm?role=" + role + "&email=" + elements.login_email.val() + "&password=" + elements.login_password.val() , 
				(response) => {
					var errors = "";
					console.log(response);
					if(response === "emailNotExist") {
						errors = "\n Email does not exist. Please sign up to continue or enter a valid email address.";
					} else if(response === "combinationNotExist") {
						errors = "\n Email and password combination is wrong.";
					} else {
						flag = true;
					}
					if(!flag) {
						$("#login_errors").html("Errors: " + errors);
					} else {							
							$("#login_errors").html("");
							elements.login_form.unbind("submit");
							elements.login_form.submit();
					}
			
		})
	})
	
})