


$(document).ready(function() {
    // 비밀번호 표시 버튼을 클릭했을 때의 동작을 정의합니다.
    $('.btn-outline-secondary').click(function() {
        // 클릭된 버튼의 부모 요소를 찾아서 해당 요소의 자식 중 입력된 비밀번호를 보여줄 input 요소를 찾습니다.
        var parent = $(this).parent();
        var passwordInput = parent.find('input[type="password"]');
        
        // input 요소의 type을 변경하여 비밀번호를 표시하거나 감춥니다.
        if (passwordInput.attr('type') === 'password') {
            passwordInput.attr('type', 'text');
        } else {
            passwordInput.attr('type', 'password');
        }
    });
});