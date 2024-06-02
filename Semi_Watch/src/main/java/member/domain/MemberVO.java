package member.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberVO {

	// < insert 용도 시작>
	
	private String userid;             // 회원아이디
	private String pw;                 // 비밀번호 (SHA-256 암호화 대상) 즉, 암호화해서 DB에 넣는다
	private String username;           // 회원명
	private String email;              // 이메일 (AES-256 암호화/(<>반대말)복호화 대상) 즉, 암호화해서 DB에 넣는다
	private String mobile;             // 연락처 (AES-256 암호화/(<>반대말)복호화 대상) 즉, 암호화해서 DB에 넣는다
	private String postcode;           // 우편번호
	private String address;            // 주소
	private String detail_address;      // 상세주소
	private String extra_address;       // 참고항목
	private String gender;             // 성별   남자:1  / 여자:2
	private String birthday;           // 생년월일
	// SHA-256 은 단방향 암호화 ==> 즉, 복호화(암호화 해제)를 할수없다! 
	// AES-256 은 양방향 암호화 ==> 즉, 암호화, 복호화(암호화 해제) 둘다 가능하다.
	private int age;                    // 나이
	private String userimg;				// 유저이미지
	
	
	public String getUserimg() {
		return userimg;
	}

	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}

	private int mileage;               // 마일리지
	private String registerday;        // 가입일자 
	private String lastpwdchangedate;  // 마지막으로 암호를 변경한 날짜  
	private int status;                // 회원탈퇴유무   1: 사용가능(가입중) / 0:사용불능(탈퇴) 
	private int idle;                  // 휴면유무      0 : 활동중  /  1 : 휴면중
	                                   // 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 1년이 지났으면 휴면으로 지정
	
	
	// < insert 용도 끝>
	
	//////////////////////////////////////////////////////////////////////////////////////////
	// < select 용도 시작>
	
	private boolean requirePwdChange = false;
	   // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true
	   // 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false
	
	// < select 용도 끝>
	//////////////////////////////////////////////////////////////////////////////////////////
	
	
	public String getUserid() {
		return userid;
	}
	
	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDetail_address() {
		return detail_address;
	}

	public void setDetail_address(String detail_address) {
		this.detail_address = detail_address;
	}

	public String getExtra_address() {
		return extra_address;
	}

	public void setExtra_address(String extra_address) {
		this.extra_address = extra_address;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	

	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	//////////////////////////////////////////////////////////////////////////////////////////

	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public String getLastpwdchangedate() {
		return lastpwdchangedate;
	}

	public void setLastpwdchangedate(String lastpwdchangedate) {
		this.lastpwdchangedate = lastpwdchangedate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIdle() {
		return idle;
	}

	public void setIdle(int idle) {
		this.idle = idle;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isRequirePwdChange() {
		return requirePwdChange;
	}

	public void setRequirePwdChange(boolean requirePwdChange) {
		this.requirePwdChange = requirePwdChange;
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	//만나이

    public int getAge() { // 만나이 구하기
    
    int age = 0;
    
    // 회원의 올해생일이 현재날짜 보다 이전이라면 
    // 만나이 = 현재년도 - 회원의 태어난년도 
    
    // 회원의 올해생일이 현재날짜 보다 이후이라면
    // 만나이 = 현재년도 - 회원의 태어난년도 - 1
    
    Date now = new Date(); // 현재시각
    SimpleDateFormat sdfmt = new SimpleDateFormat("yyyyMMdd");
    String str_now = sdfmt.format(now); // "20231018"
    
    // 회원의 올해생일(문자열 타입)
    String str_now_birthday = str_now.substring(0, 4) + birthday.substring(5,7) + birthday.substring(8); 
    //   System.out.println("회원의 올해생일(문자열 타입) : " + str_now_birthday);
    // 회원의 올해생일(문자열 타입) : 20231020
    
    // 회원의 태어난년도
    int birth_year = Integer.parseInt(birthday.substring(0, 4));
    
    // 현재년도
    int now_year = Integer.parseInt(str_now.substring(0, 4)); 
    
    try {
    Date now_birthday = sdfmt.parse(str_now_birthday); // 회원의 올해생일(연월일) 날짜 타입 
    now = sdfmt.parse(str_now); // 오늘날짜(연월일) 날짜타입
    
    if(now_birthday.before(now)) {
    // 회원의 올해생일이 현재날짜 보다 이전이라면
    //   System.out.println("~~~~ 생일 지남");
    age = now_year - birth_year; 
    // 나이 = 현재년도 - 회원의 태어난년도
    }
    else {
    // 회원의 올해생일이 현재날짜 보다 이후이라면
    //   System.out.println("~~~~ 생일 아직 지나지 않음");
    age = now_year - birth_year - 1;
    // 나이 = 현재년도 - 회원의 태어난년도 - 1
    }
    
    } catch (ParseException e) {
    
    }
    
    return age;
    }
	
	
	
	
	
	
	
}
