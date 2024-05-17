

$(document).ready(function(){
	
	$("textarea").hide();
	
});	// end of $(document).ready(function() -------

// Function Declaration
function gochange(){

    let userInfo = document.getElementById('userInfo');
    let chageInfo = document.getElementById('chageInfo');

    chageInfo.style.display = "block";
    userInfo.style.display = "none";
    
}

function gochange_end(){

    let userInfo = document.getElementById('userInfo');
    let chageInfo = document.getElementById('chageInfo');

    chageInfo.style.display = "none";
    userInfo.style.display = "block";

}