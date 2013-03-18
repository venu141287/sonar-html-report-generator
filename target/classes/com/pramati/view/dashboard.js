<script type="text/javascript">
	function getEmail() {
		emailAddress = prompt("Enter Email Address to send Sonar Report", "");
		validated = ValidateEmail(emailAddress);
		var url = window.location.protocol + "//" + window.location.host + window.location.pathname;
		if(validated){
		if (window.XMLHttpRequest)
		  {// code for IE7+, Firefox, Chrome, Opera, Safari
		  xmlhttp=new XMLHttpRequest();
		  }
		else
		  {// code for IE6, IE5
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		  }
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
		    document.getElementById("status").innerHTML="mail sent"
		    }
		  }
		xmlhttp.open("POST","/html/email",true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("email="+emailAddress+"&url="+url);
	}
	}
	function ValidateEmail(inputText) {
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (!filter.test(inputText)) {
			alert('Please provide a valid email address');
			return false;
		}
		return true;
	}
</script>