
$(document).ready(function(){

    // 변경 row 안보이게 함
    $("tr#change_password_area").hide();
    $("tr#change_email_area").hide();

    // $("table#userinfo").find("tr#change_password_area").hide();
    // $("table#userinfo").find("tr#change_email_area").hide();


});// end of $(document).ready(function()------

function change_password(){
    $("tr#password_area").hide();
    $("tr#change_password_area").show();
}

function change_email(){
    $("tr#email_area").hide();
    $("tr#change_email_area").show();
}

function password_masking() {
    
    const currentpwd= $("input#currentpwd").val();

    // 비밀번호가 변경되었다고 가정하고, 실제 비밀번호를 *로 대체하여 표시
    const maskedPwd = '*'.repeat(currentPwd.length);
    
    $("td#cpwdview").text(maskedPwd);
}