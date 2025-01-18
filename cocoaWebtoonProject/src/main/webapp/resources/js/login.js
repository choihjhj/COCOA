$(() => {
   const signupResult = $('#signupResult').val();
   signupcheck(signupResult);
   function signupcheck(val) {
      if (val === '') {
         return;
      } else if (val === '1') {
         alert(" 회원가입 성공");
      } else if (val === '0') {
         alert(" 회원가입 실패");
      }
   }

   const loginResult = $('#loginResult').val();
   logincheck(loginResult);
   function logincheck(val) {
      if (val === '') {
         return;
      } else if (val == '0') {
         alert("로그인 실패. 등록되지 않은 회원이거나 비밀번호가 일치하지 않습니다.");
      }
   }

   function resetClass(element, classname) {
      element.classList.remove(classname);
   }

   let action = 'signin';

   document.getElementsByClassName("show-signup")[0].addEventListener("click", function () {
      let form = document.getElementsByClassName("form")[0];
      resetClass(form, "signin");
      form.classList.add("signup");
      document.getElementById("submit-btn").innerText = "회원가입";
      action = 'signup';


      $("#pwd1").attr("required", "required");
      $("input[name='userName']").attr("required", "required");
      $("input[name='phone']").attr("required", "required");
      $("input[name='birthday']").attr("required", "required");


   });

   document.getElementsByClassName("show-signin")[0].addEventListener("click", function () {
      let form = document.getElementsByClassName("form")[0];
      resetClass(form, "signup");
      form.classList.add("signin");
      document.getElementById("submit-btn").innerText = "로그인";
      action = 'signin';



      $("#pwd1").removeAttr("required");
      $("input[name='userName']").removeAttr("required");
      $("input[name='phone']").removeAttr("required");
      $("input[name='birthday']").removeAttr("required");

   });

   document.getElementsByClassName("show-signin")[0].click();

   $("#submit-btn").click((e) => {

      e.preventDefault(); // 버튼 클릭했을 때 이벤트의 기본적인 행동 중단(여기선 페이지 이동)

      if (action === 'signup') {

         const pwdObj = $('#pwd')

         if (pwdObj.val() != $("#pwd1").val()) {
            alert('비밀번호가 일치하지 않습니다')
            pwdObj.focus();
            return
         } else {

            const userId = $('input[name="userId"]').val()

            $.ajax({
               url: "/iddupcheck",
               method: "GET",
               data: {"userId" : userId},
               success: (responseObj) => {
                  if (responseObj == 0) {//중복되지 않은경우
                     $("form").submit(); // submit 수행
                  } else {
                     alert("아이디가 중복됩니다.")
                     $('input[name="userId"]').focus()
                  }
               },
               error: (xhr) => {
                  alert("으악:" + xhr.status)
               }
            })
         }



      } else if (action === 'signin') {

         $("form").attr("action", "/login")
         $("form").submit()

      }
   });

   function getParameterByName(name, url) {
      if (!url) url = window.location.href;
      name = name.replace(/[\[\]]/g, "\\$&");
      let regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
          results = regex.exec(url);
      if (!results) return null;
      if (!results[2]) return '';
      return decodeURIComponent(results[2].replace(/\+/g, " "));
  }
  
  
   $(document).ready(function() {
      
      let redirectValue = getParameterByName('redirect');

      if (redirectValue) {
          $('#redirectURL').val(redirectValue);
      }
      
      console.log(redirectValue);
  });


});

